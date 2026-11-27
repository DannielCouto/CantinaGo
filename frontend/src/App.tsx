import { useEffect, useState } from 'react'
import { fetchReservableEBikes, fetchStations, type EBike, type EBikeType, type Station } from './api'

const REFRESH_MS = 5000

const TYPE_LABEL: Record<EBikeType, string> = { NORMAL: 'Normal', CARGO: 'Carga' }

export default function App() {
  const [stations, setStations] = useState<Station[]>([])
  const [stationId, setStationId] = useState<number | null>(null)
  const [type, setType] = useState<EBikeType | ''>('')
  const [ebikes, setEbikes] = useState<EBike[] | null>(null)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    fetchStations()
      .then((list) => {
        setStations(list)
        if (list.length > 0) setStationId(list[0].id)
      })
      .catch((e: Error) => setError(`Não foi possível carregar as estações (${e.message}).`))
  }, [])

  // US7.4: reservable e-bikes at the selected station, refreshed so telemetry changes show up.
  useEffect(() => {
    if (stationId === null) return
    let active = true
    const load = () =>
      fetchReservableEBikes(stationId, type || undefined)
        .then((list) => {
          if (active) {
            setEbikes(list)
            setError(null)
          }
        })
        .catch((e: Error) => active && setError(`Não foi possível carregar as e-bikes (${e.message}).`))
    load()
    const timer = setInterval(load, REFRESH_MS)
    return () => {
      active = false
      clearInterval(timer)
    }
  }, [stationId, type])

  return (
    <main className="page">
      <header className="masthead">
        <h1>CantinaGo</h1>
        <p className="lede">E-bikes disponíveis por estação</p>
      </header>

      <section className="filters" aria-label="Filtros">
        <label>
          Estação
          <select
            value={stationId ?? ''}
            onChange={(e) => setStationId(Number(e.target.value))}
            disabled={stations.length === 0}
          >
            {stations.map((s) => (
              <option key={s.id} value={s.id}>
                {s.name}
              </option>
            ))}
          </select>
        </label>
        <label>
          Tipo
          <select value={type} onChange={(e) => setType(e.target.value as EBikeType | '')}>
            <option value="">Todos</option>
            <option value="NORMAL">Normal</option>
            <option value="CARGO">Carga</option>
          </select>
        </label>
      </section>

      {error && (
        <p role="alert" className="error">
          {error}
        </p>
      )}

      {ebikes && ebikes.length === 0 && <p className="empty">Nenhuma e-bike reservável nesta estação.</p>}

      {ebikes && ebikes.length > 0 && (
        <ul className="bikes" aria-label="E-bikes reserváveis">
          {ebikes.map((b) => (
            <li key={b.code} className="bike">
              <span className="code">{b.code}</span>
              <span className="type">{TYPE_LABEL[b.type]}</span>
              <span className="battery" aria-label={`Bateria ${b.battery} %`}>
                <span className="bar" style={{ width: `${b.battery}%` }} />
                <span className="pct">{b.battery} %</span>
              </span>
            </li>
          ))}
        </ul>
      )}
    </main>
  )
}
