import UsuarioService from "./usuarioService";

describe("Deve validar UsuarioService", () => {
  test("Retorna CPF válido quando informar CPF válido", () => {
    expect(UsuarioService.validarCpfValido("103.324.589-54")).toBe(true);
  });

  test("Retorna CPF válido quando informar CPF inválido", () => {
    expect(() => {
      UsuarioService.validarCpfValido("103.324.589-55");
    }).toThrow("O CPF 103.324.589-55 está inválido.");
  });

  test("Deve retornar true se usuário possuir permissão", () => {
    expect(
      UsuarioService.validarPermissaoDoUsuario("123456", { id: "123456" })
    ).toBe(true);
  });

  test("Deve lançar erro quando usuário não possuir permissão", () => {
    expect(() => {
      UsuarioService.validarPermissaoDoUsuario("12345", { id: "123456" });
    }).toThrow("Você não possui permissão para visualizar este recurso.");
  });
});
