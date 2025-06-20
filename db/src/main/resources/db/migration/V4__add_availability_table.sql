CREATE SEQUENCE availability_id_sequence;
CREATE TABLE AVAILABILITY
(
    ID             INTEGER NOT NULL DEFAULT nextval('availability_id_sequence'),
    VERSION        INTEGER NOT NULL DEFAULT 0,
    AVAILABLE_DATE DATE    NOT NULL,
    STATUS_ID      INTEGER NOT NULL REFERENCES STATUS (ID),
    PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX available_date_idx ON AVAILABILITY (AVAILABLE_DATE);

ALTER TABLE AVAILABILITY
    ADD CONSTRAINT unique_availability_id
        UNIQUE USING INDEX available_date_idx;