import { render, screen, within } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { afterEach, describe, expect, it, vi } from 'vitest'
import App from './App'

const stations = [
  { id: 1, name: 'DETI (edifício 4)', latitude: 40.63, longitude: -8.66, capacity: 10 },
  { id: 3, name: 'Cantina do Crasto', latitude: 40.62, longitude: -8.66, capacity: 8 },
]

function mockApi(routes: Record<string, unknown>) {
  const fetchMock = vi.fn(async (url: string) => {
    if (!(url in routes)) return new Response('not found', { status: 404 })
    return new Response(JSON.stringify(routes[url]), { status: 200 })
  })
  vi.stubGlobal('fetch', fetchMock)
  return fetchMock
}

afterEach(() => vi.unstubAllGlobals())

describe('App', () => {
  it('shows reservable e-bikes of the first station', async () => {
    mockApi({
      '/api/stations': stations,
      '/api/stations/1/ebikes': [{ code: 'EB-01', type: 'NORMAL', status: 'AVAILABLE', battery: 92 }],
    })

    render(<App />)

    const list = await screen.findByRole('list', { name: 'E-bikes reserváveis' })
    expect(within(list).getByText('EB-01')).toBeInTheDocument()
    expect(within(list).getByLabelText('Bateria 92 %')).toBeInTheDocument()
  })

  it('filters by station and type', async () => {
    const fetchMock = mockApi({
      '/api/stations': stations,
      '/api/stations/1/ebikes': [],
      '/api/stations/3/ebikes': [],
      '/api/stations/3/ebikes?type=CARGO': [{ code: 'EB-11', type: 'CARGO', status: 'AVAILABLE', battery: 79 }],
    })
    const user = userEvent.setup()

    render(<App />)
    await screen.findByText('Nenhuma e-bike reservável nesta estação.')
    await user.selectOptions(screen.getByLabelText('Estação'), '3')
    await user.selectOptions(screen.getByLabelText('Tipo'), 'CARGO')

    expect(await screen.findByText('EB-11')).toBeInTheDocument()
    expect(fetchMock).toHaveBeenCalledWith('/api/stations/3/ebikes?type=CARGO')
  })

  it('shows an error when the API is down', async () => {
    vi.stubGlobal('fetch', vi.fn(async () => new Response('', { status: 503, statusText: 'Service Unavailable' })))

    render(<App />)

    expect(await screen.findByRole('alert')).toHaveTextContent('Não foi possível carregar as estações')
  })
})
