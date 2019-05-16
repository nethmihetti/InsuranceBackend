CREATE TABLE main.address(
 address_id serial PRIMARY KEY,
 house_num VARCHAR (50)  NOT NULL,
 apartment_num VARCHAR (50) NULL,
 street VARCHAR (100)  NOT NULL,
 city VARCHAR (100)  NOT NULL,
 state VARCHAR (100)  NOT NULL,
 country VARCHAR (50)  NOT NULL,
 UNIQUE (house_num, apartment_num, street, city, state, country)
);

CREATE TABLE main.user_data(
 user_id serial PRIMARY KEY,
 first_name VARCHAR (50)  NOT NULL,
 middle_name VARCHAR (50)  NOT NULL,
 last_name VARCHAR (50)  NOT NULL,
 email VARCHAR (320) UNIQUE NOT NULL,
 mobile_num VARCHAR (20) UNIQUE NOT NULL,
 passport_num VARCHAR (20) UNIQUE NOT NULL,
 passport_issued_by VARCHAR (150)  NOT NULL,
 passport_issued_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE main.company_details(
  company_id serial PRIMARY KEY,
  company_name VARCHAR(200) NOT NULL,
  address_id serial NOT NULL REFERENCES main.address(address_id)
);

CREATE TABLE main.insurance_request (
  insurance_request_id serial PRIMARY KEY,
  user_id serial NOT NULL REFERENCES main.user_data(user_id),
  property_type VARCHAR (50)  NOT NULL,
  amount NUMERIC(15,2)  NOT NULL,
  policy_start_date DATE NOT NULL DEFAULT CURRENT_DATE,
  policy_end_date DATE NOT NULL DEFAULT CURRENT_DATE,
  policy_created_date DATE NOT NULL DEFAULT CURRENT_DATE,
  address_id serial NOT NULL REFERENCES main.address(address_id),
  status VARCHAR (20) NOT NULL DEFAULT 'pending',
  company_id serial NOT NULL REFERENCES main.company_details(company_id)
);

INSERT INTO main.address(house_num, apartment_num, street, city, state, country)
VALUES
('123', 'Dorm3', '1,University st', 'Innopolis', 'Tatarstan', 'Russia');

INSERT INTO main.user_data(first_name, middle_name, last_name, email, mobile_num, passport_num, passport_issued_by, passport_issued_date)
VALUES
('Nethmi', 'Thileka', 'Hettiarachchi', 'nethmihettiarachchi484@gmail.com', '+102284587584', '12ws34re', 'Sri Lanka', '2016-06-22');

INSERT INTO main.company_details(company_name, address_id)
VALUES
('ABC Insurance', 1)