CREATE TABLE main.address(
 addressId serial PRIMARY KEY,
 houseNum VARCHAR (50)  NOT NULL,
 apartmentNum VARCHAR (50) NULL,
 street VARCHAR (100)  NOT NULL,
 city VARCHAR (100)  NOT NULL,
 state VARCHAR (100)  NOT NULL,
 country VARCHAR (50)  NOT NULL,
 UNIQUE (houseNum, apartmentNum, street, city, state, country)
);

CREATE TABLE main.userData(
 userId serial PRIMARY KEY,
 username VARCHAR (50) UNIQUE NOT NULL,
 firstName VARCHAR (50)  NOT NULL,
 middleName VARCHAR (50)  NOT NULL,
 lastName VARCHAR (50)  NOT NULL,
 password VARCHAR (200) NOT NULL,
 email VARCHAR (320) UNIQUE NOT NULL,
 mobileNum VARCHAR (20) UNIQUE NOT NULL,
 passportNum VARCHAR (20) UNIQUE NOT NULL,
 passportIssuedBy VARCHAR (150)  NOT NULL,
 passportIssuedDate DATE NOT NULL DEFAULT CURRENT_DATE,
 addressId serial NOT NULL REFERENCES main.address(addressId)
);

create type insurance_status as enum('pending', 'accepted', 'rejected');

CREATE TABLE main.insuranceRequest (
  insuranceRequestId serial PRIMARY KEY,
  userId serial NOT NULL REFERENCES main.userData(userId),
  propertyType VARCHAR (50)  NOT NULL,
  amount NUMERIC  NOT NULL,
  policyStartDate DATE NOT NULL DEFAULT CURRENT_DATE,
  policyEndDate DATE NOT NULL DEFAULT CURRENT_DATE,
  policyCreatedDate DATE NOT NULL DEFAULT CURRENT_DATE,
  status insurance_status NOT NULL DEFAULT 'pending'
);

INSERT INTO main.address(houseNum, apartmentNum, street, city, state, country)
VALUES
('123', 'Dorm3', '1,University st', 'Innopolis', 'Tatarstan', 'Russia');

INSERT INTO main.userData(username, firstName, middleName, lastName, password, email, mobileNum, passportNum, passportIssuedBy, passportIssuedDate)
VALUES
('nethmih', 'Nethmi', 'Thileka', 'Hettiarachchi', '', 'nethmihettiarachchi484@gmail.com', '+102284587584', '12ws34re', 'Sri Lanka', '2016-06-22');