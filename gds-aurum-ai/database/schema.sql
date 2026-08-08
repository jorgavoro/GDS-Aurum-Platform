
-- =====================================================
-- GDS Aurum AI Platform
-- MVP 1 - PostgreSQL Database Schema
-- =====================================================

CREATE SCHEMA IF NOT EXISTS aurum;
SET search_path TO aurum;

-- =========================
-- Security
-- =========================
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(150) NOT NULL
);

CREATE TABLE role_permissions (
    role_id BIGINT REFERENCES roles(id) ON DELETE CASCADE,
    permission_id BIGINT REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY(role_id, permission_id)
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(80) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(200),
    email VARCHAR(200),
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY(user_id, role_id)
);

-- =========================
-- Organization
-- =========================
CREATE TABLE companies (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE,
    name VARCHAR(200) NOT NULL,
    tax_id VARCHAR(30),
    currency_code VARCHAR(10),
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE branches (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES companies(id),
    code VARCHAR(20),
    name VARCHAR(150)
);

CREATE TABLE fiscal_years (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES companies(id),
    year SMALLINT NOT NULL,
    status VARCHAR(20) DEFAULT 'OPEN'
);

CREATE TABLE fiscal_periods (
    id BIGSERIAL PRIMARY KEY,
    fiscal_year_id BIGINT REFERENCES fiscal_years(id),
    period SMALLINT NOT NULL,
    start_date DATE,
    end_date DATE,
    status VARCHAR(20) DEFAULT 'OPEN'
);

-- =========================
-- Accounting
-- =========================
CREATE TABLE account_categories(
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(10),
    name VARCHAR(100)
);

CREATE TABLE accounts(
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT REFERENCES accounts(id),
    category_id BIGINT REFERENCES account_categories(id),
    code VARCHAR(30) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    level_no INT,
    nature CHAR(1),
    accepts_movements BOOLEAN DEFAULT TRUE,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE journals(
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES companies(id),
    period_id BIGINT REFERENCES fiscal_periods(id),
    voucher_number VARCHAR(50),
    journal_date DATE NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'DRAFT'
);

CREATE TABLE journal_details(
    id BIGSERIAL PRIMARY KEY,
    journal_id BIGINT REFERENCES journals(id) ON DELETE CASCADE,
    account_id BIGINT REFERENCES accounts(id),
    third_party_id BIGINT,
    debit NUMERIC(18,2) DEFAULT 0,
    credit NUMERIC(18,2) DEFAULT 0,
    description TEXT
);

-- =========================
-- Master Data
-- =========================
CREATE TABLE customers(
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(30) UNIQUE,
    document_number VARCHAR(30),
    name VARCHAR(200),
    email VARCHAR(200),
    phone VARCHAR(50),
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE suppliers(
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(30) UNIQUE,
    document_number VARCHAR(30),
    name VARCHAR(200),
    email VARCHAR(200),
    phone VARCHAR(50),
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE product_categories(
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(30),
    name VARCHAR(100)
);

CREATE TABLE products(
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT REFERENCES product_categories(id),
    sku VARCHAR(60) UNIQUE,
    name VARCHAR(200),
    unit VARCHAR(20),
    sale_price NUMERIC(18,2),
    purchase_price NUMERIC(18,2),
    inventory_item BOOLEAN DEFAULT TRUE,
    active BOOLEAN DEFAULT TRUE
);

-- =========================
-- Sales
-- =========================
CREATE TABLE invoices(
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT REFERENCES customers(id),
    invoice_number VARCHAR(50),
    invoice_date DATE,
    subtotal NUMERIC(18,2),
    tax NUMERIC(18,2),
    total NUMERIC(18,2),
    status VARCHAR(20) DEFAULT 'DRAFT'
);

CREATE TABLE invoice_details(
    id BIGSERIAL PRIMARY KEY,
    invoice_id BIGINT REFERENCES invoices(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    quantity NUMERIC(18,2),
    unit_price NUMERIC(18,2),
    tax NUMERIC(18,2),
    total NUMERIC(18,2)
);

-- =========================
-- Treasury
-- =========================
CREATE TABLE receipts(
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT REFERENCES customers(id),
    receipt_number VARCHAR(50),
    receipt_date DATE,
    amount NUMERIC(18,2),
    payment_method VARCHAR(30)
);

-- =========================
-- Audit
-- =========================
CREATE TABLE audit_log(
    id BIGSERIAL PRIMARY KEY,
    entity_name VARCHAR(100),
    entity_id BIGINT,
    action VARCHAR(20),
    username VARCHAR(80),
    event_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payload JSONB
);

CREATE INDEX idx_accounts_parent ON accounts(parent_id);
CREATE INDEX idx_journal_date ON journals(journal_date);
CREATE INDEX idx_invoice_customer ON invoices(customer_id);

COMMENT ON SCHEMA aurum IS 'GDS Aurum AI Platform - MVP1';
