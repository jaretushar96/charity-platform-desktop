CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    mobile_number VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    address TEXT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    problem VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    hashed_password TEXT NOT NULL,
    profile_pic_path TEXT,
    upi_qr_path TEXT,
    in_donate_now BOOLEAN DEFAULT FALSE
);
