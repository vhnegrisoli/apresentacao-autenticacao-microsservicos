import * as config from "./secrets";

const CADEIRA_LIVRE_API = `${config.CADEIRA_LIVRE_ADMIN_BASE_URI}/api/cadeiras-livres`;
const EMPRESA_API = `${config.CADEIRA_LIVRE_ADMIN_BASE_URI}/api/empresas`;

export const ENDPOINT_CADEIRAS_LIVRES_DO_CLIENTE = `${CADEIRA_LIVRE_API}/cliente-api?token={token}`;
export const ENDPOINT_CADEIRAS_LIVRES_DISPONIVEIS = `${CADEIRA_LIVRE_API}/disponiveis/cliente-api?token={token}&empresaId={empresaId}`;
export const ENDPOINT_CADEIRAS_LIVRES_POR_ID = `${CADEIRA_LIVRE_API}/{cadeiraLivreId}/cliente-api?token={token}`;
export const ENDPOINT_RESERVAR_CADEIRA_LIVRE = `${CADEIRA_LIVRE_API}/{cadeiraLivreId}/cliente-api/reservar?token={token}`;

export const ENDPOINT_BUSCAR_EMPRESAS = `${EMPRESA_API}/cliente-api?token={token}`;
export const ENDPOINT_BUSCAR_EMPRESA_POR_ID = `${EMPRESA_API}/{empresaId}/cliente-api?token={token}&empresaId={empresaId}`;
