-- Banking schema for Etapa II (H2 compatible)
DROP TABLE IF EXISTS tranzactii;
DROP TABLE IF EXISTS carduri;
DROP TABLE IF EXISTS conturi;
DROP TABLE IF EXISTS clienti;

CREATE TABLE clienti (
  cnp VARCHAR(32) PRIMARY KEY,
  nume VARCHAR(200) NOT NULL,
  adresa VARCHAR(500)
);

CREATE TABLE conturi (
  iban VARCHAR(64) PRIMARY KEY,
  sold DOUBLE,
  moneda VARCHAR(10),
  client_cnp VARCHAR(32) NOT NULL,
  tip VARCHAR(20),
  limita_descoperit DOUBLE,
  rata_dobanda DOUBLE,
  FOREIGN KEY (client_cnp) REFERENCES clienti(cnp)
);

CREATE TABLE carduri (
  numar_card VARCHAR(32) PRIMARY KEY,
  pin VARCHAR(16) NOT NULL,
  stare_activa BOOLEAN,
  iban_cont VARCHAR(64) NOT NULL,
  FOREIGN KEY (iban_cont) REFERENCES conturi(iban)
);

CREATE TABLE tranzactii (
  id INT AUTO_INCREMENT PRIMARY KEY,
  iban_cont VARCHAR(64) NOT NULL,
  suma DOUBLE NOT NULL,
  tip VARCHAR(100),
  data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (iban_cont) REFERENCES conturi(iban)
);
