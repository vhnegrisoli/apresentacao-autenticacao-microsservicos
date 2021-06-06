module.exports = {
  openapi: "3.0.1",
  info: {
    version: "1.0.0",
    title: "Cadeira Livre Cliente API",
    description: "API do cliente do app SalonB",
    license: {
      name: "MIT",
    },
  },
  servers: [
    {
      url: "http://localhost:8096/",
      description: "API local",
    },
    {
      url: "https://cadeira-livre-cliente-api.herokuapp.com/",
      description: "API produção",
    },
  ],
  security: [
    {
      ApiKeyAuth: [],
    },
  ],
  tags: [
    {
      name: "Autenticação",
    },
  ],
  paths: {
    "/api/auth/token": {
      post: {
        tags: ["Autenticação"],
        description: "Gerar token JWT",
        operationId: "autenticarUsuario",
        parameters: [],
        requestBody: {
          content: {
            "application/json": {
              schema: {
                $ref: "#/components/schemas/Auth",
              },
            },
          },
          required: true,
        },
        responses: {
          200: {
            description: "O token JWT foi gerado.",
            content: {
              "application/json": {
                schema: {
                  $ref: "#/components/schemas/AccessToken",
                },
              },
            },
          },
          400: {
            description: "Erro ao gerar Access Token",
            content: {
              "application/json": {
                schema: {
                  $ref: "#/components/schemas/Error",
                },
              },
            },
          },
        },
      },
    },
  },
  components: {
    schemas: {
      Auth: {
        type: "object",
        properties: {
          email: {
            type: "string",
          },
          senha: {
            type: "string",
          },
        },
      },
      AccessToken: {
        type: "object",
        properties: {
          token: {
            type: "string",
          },
          status: {
            type: "number",
          },
        },
      },
      Error: {
        type: "object",
        properties: {
          status: {
            type: "number",
          },
          message: {
            type: "string",
          },
        },
      },
    },
  },
  securitySchemes: {
    ApiKeyAuth: {
      type: "apiKey",
      in: "header",
      name: "Authorization",
    },
  },
};
