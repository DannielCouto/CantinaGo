export type EBikeType = 'NORMAL' | 'CARGO'

export interface Station {
  id: number
  name: string
  latitude: number
  longitude: number
  capacity: number
}

export interface EBike {
  code: string
  type: EBikeType
  status: string
  battery: number
}

async function getJson<T>(url: string): Promise<T> {
  const res = await fetch(url)
  if (!res.ok) {
    throw new Error(`${res.status} ${res.statusText}`)
  }
  return res.json() as Promise<T>
}

export function fetchStations(): Promise<Station[]> {
  return getJson<Station[]>('/api/stations')
}

export function fetchReservableEBikes(stationId: number, type?: EBikeType): Promise<EBike[]> {
  const query = type ? `?type=${type}` : ''
  return getJson<EBike[]>(`/api/stations/${stationId}/ebikes${query}`)
}
