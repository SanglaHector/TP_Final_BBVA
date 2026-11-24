// ============================================================================
// Script de Inicialización de MongoDB
// Crea la base de datos y colecciones necesarias
// ============================================================================

// Cambiar a la base de datos tienda_camisetas
db = db.getSiblingDB('tienda_camisetas');

// Crear usuario específico para la aplicación
db.createUser({
    user: "app_user",
    pwd: "app_password_123",
    roles: [
        { role: "readWrite", db: "tienda_camisetas" }
    ]
});

print("✓ Usuario 'app_user' creado");

// Crear colecciones principales
db.createCollection("paises", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["nombre"],
            properties: {
                _id: { bsonType: "objectId" },
                nombre: { bsonType: "string" },
                createdAt: { bsonType: "date" },
                updatedAt: { bsonType: "date" }
            }
        }
    }
});

print("✓ Colección 'paises' creada");

db.createCollection("competiciones", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["nombre", "pais"],
            properties: {
                _id: { bsonType: "objectId" },
                nombre: { bsonType: "string" },
                pais: { bsonType: "objectId" },
                createdAt: { bsonType: "date" },
                updatedAt: { bsonType: "date" }
            }
        }
    }
});

print("✓ Colección 'competiciones' creada");

db.createCollection("equipos", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["nombre", "pais", "competicion", "tipo"],
            properties: {
                _id: { bsonType: "objectId" },
                nombre: { bsonType: "string" },
                pais: { bsonType: "objectId" },
                competicion: { bsonType: "objectId" },
                tipo: { bsonType: "string", enum: ["LOCAL", "INTERNACIONAL"] },
                createdAt: { bsonType: "date" },
                updatedAt: { bsonType: "date" }
            }
        }
    }
});

print("✓ Colección 'equipos' creada");

db.createCollection("camisetas", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["equipo", "temporada", "precio", "talle", "stock", "marca"],
            properties: {
                _id: { bsonType: "objectId" },
                equipo: { bsonType: "objectId" },
                temporada: { bsonType: "string" },
                precio: { bsonType: "double", minimum: 0 },
                talle: { bsonType: "string", enum: ["XS", "S", "M", "L", "XL", "XXL"] },
                stock: { bsonType: "int", minimum: 0 },
                marca: { bsonType: "string" },
                esRetro: { bsonType: "bool" },
                createdAt: { bsonType: "date" },
                updatedAt: { bsonType: "date" }
            }
        }
    }
});

print("✓ Colección 'camisetas' creada");

// Crear índices para optimizar búsquedas
db.paises.createIndex({ nombre: 1 }, { unique: true });
print("✓ Índice en 'paises.nombre' creado");

db.competiciones.createIndex({ nombre: 1 });
db.competiciones.createIndex({ pais: 1 });
print("✓ Índices en 'competiciones' creados");

db.equipos.createIndex({ nombre: 1 }, { unique: true });
db.equipos.createIndex({ pais: 1 });
db.equipos.createIndex({ competicion: 1 });
db.equipos.createIndex({ tipo: 1 });
print("✓ Índices en 'equipos' creados");

db.camisetas.createIndex({ equipo: 1 });
db.camisetas.createIndex({ temporada: 1 });
db.camisetas.createIndex({ marca: 1 });
db.camisetas.createIndex({ talle: 1 });
db.camisetas.createIndex({ esRetro: 1 });
db.camisetas.createIndex({ precio: 1 });
db.camisetas.createIndex({ "equipo": 1, "temporada": 1 }, { unique: true });
print("✓ Índices en 'camisetas' creados");

// Insertar datos de ejemplo (Opcional)
// Países
db.paises.insertMany([
    { nombre: "Argentina", createdAt: new Date(), updatedAt: new Date() },
    { nombre: "Brasil", createdAt: new Date(), updatedAt: new Date() },
    { nombre: "Uruguay", createdAt: new Date(), updatedAt: new Date() },
    { nombre: "Paraguay", createdAt: new Date(), updatedAt: new Date() },
    { nombre: "Chile", createdAt: new Date(), updatedAt: new Date() },
    { nombre: "España", createdAt: new Date(), updatedAt: new Date() },
    { nombre: "Italia", createdAt: new Date(), updatedAt: new Date() },
    { nombre: "Alemania", createdAt: new Date(), updatedAt: new Date() },
    { nombre: "Francia", createdAt: new Date(), updatedAt: new Date() },
    { nombre: "Inglaterra", createdAt: new Date(), updatedAt: new Date() }
]);

print("✓ 10 países insertados");

// Mostrar resumen
print("\n========================================");
print("✅ INICIALIZACIÓN COMPLETADA");
print("========================================");
print("Base de datos: tienda_camisetas");
print("Usuario: admin");
print("Usuario App: app_user");
print("Colecciones: 4 (paises, competiciones, equipos, camisetas)");
print("Índices: 14 creados para optimización");
print("Documentos de ejemplo: 10 países");
print("========================================\n");

