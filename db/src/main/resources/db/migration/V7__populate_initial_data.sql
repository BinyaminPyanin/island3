--STATUS
insert into public.STATUS(CODE,DESCRIPTION) values ('AVAILABLE','Resource is available');
insert into public.STATUS(CODE,DESCRIPTION) values ('RESERVED','Resource is reserved');
insert into public.STATUS(CODE,DESCRIPTION) values ('CANCELED','Resource is canceled');

--SPOT
insert into public.SPOT(NAME,DESCRIPTION,STATUS_ID) values ('Retriever labrador','Retriever spot',1);
insert into public.SPOT(NAME,DESCRIPTION,STATUS_ID) values ('German Shepherd Dogs	spot','German Shepherd Dogs spot',1);
insert into public.SPOT(NAME,DESCRIPTION,STATUS_ID) values ('Retrievers (Golden)','Retrievers (Golden)  spot',1);

--AVAILABILITY
--March 2021 is available for booking
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-15',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-16',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-17',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-18',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-19',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-20',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-21',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-22',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-23',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-24',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-25',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-26',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-27',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-02-28',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-01',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-02',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-03',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-04',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-05',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-06',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-07',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-08',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-09',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-10',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-11',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-12',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-13',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-14',1);
insert into public.AVAILABILITY(AVAILABLE_DATE,STATUS_ID) values ('2021-03-15',1);
