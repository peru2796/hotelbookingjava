--
-- PostgreSQL database dump
--

\restrict LUWazTmv5B3X226HVDROFJZ2QmJqrG6gbW42DyPzGLPW2Dj5DDhYSLFjxshmP9Y

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

-- Started on 2025-12-11 19:34:22

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 16388)
-- Name: hotelapp; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA hotelapp;


ALTER SCHEMA hotelapp OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 221 (class 1259 OID 16390)
-- Name: booking; Type: TABLE; Schema: hotelapp; Owner: postgres
--

CREATE TABLE hotelapp.booking (
    id bigint NOT NULL,
    booking_date date,
    booking_details character varying(255),
    status integer
);


ALTER TABLE hotelapp.booking OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16389)
-- Name: booking_id_seq; Type: SEQUENCE; Schema: hotelapp; Owner: postgres
--

CREATE SEQUENCE hotelapp.booking_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE hotelapp.booking_id_seq OWNER TO postgres;

--
-- TOC entry 5078 (class 0 OID 0)
-- Dependencies: 220
-- Name: booking_id_seq; Type: SEQUENCE OWNED BY; Schema: hotelapp; Owner: postgres
--

ALTER SEQUENCE hotelapp.booking_id_seq OWNED BY hotelapp.booking.id;


--
-- TOC entry 223 (class 1259 OID 16398)
-- Name: client; Type: TABLE; Schema: hotelapp; Owner: postgres
--

CREATE TABLE hotelapp.client (
    id bigint NOT NULL,
    email character varying(255),
    name character varying(255),
    status integer
);


ALTER TABLE hotelapp.client OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16397)
-- Name: client_id_seq; Type: SEQUENCE; Schema: hotelapp; Owner: postgres
--

CREATE SEQUENCE hotelapp.client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE hotelapp.client_id_seq OWNER TO postgres;

--
-- TOC entry 5079 (class 0 OID 0)
-- Dependencies: 222
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: hotelapp; Owner: postgres
--

ALTER SEQUENCE hotelapp.client_id_seq OWNED BY hotelapp.client.id;


--
-- TOC entry 229 (class 1259 OID 16511)
-- Name: floors; Type: TABLE; Schema: hotelapp; Owner: postgres
--

CREATE TABLE hotelapp.floors (
    id bigint NOT NULL,
    floor_name character varying(255) NOT NULL,
    floor_number integer NOT NULL,
    status integer
);


ALTER TABLE hotelapp.floors OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16510)
-- Name: floors_id_seq; Type: SEQUENCE; Schema: hotelapp; Owner: postgres
--

CREATE SEQUENCE hotelapp.floors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE hotelapp.floors_id_seq OWNER TO postgres;

--
-- TOC entry 5080 (class 0 OID 0)
-- Dependencies: 228
-- Name: floors_id_seq; Type: SEQUENCE OWNED BY; Schema: hotelapp; Owner: postgres
--

ALTER SEQUENCE hotelapp.floors_id_seq OWNED BY hotelapp.floors.id;


--
-- TOC entry 225 (class 1259 OID 16408)
-- Name: report; Type: TABLE; Schema: hotelapp; Owner: postgres
--

CREATE TABLE hotelapp.report (
    id bigint NOT NULL,
    description character varying(255),
    report_name character varying(255),
    status integer
);


ALTER TABLE hotelapp.report OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16407)
-- Name: report_id_seq; Type: SEQUENCE; Schema: hotelapp; Owner: postgres
--

CREATE SEQUENCE hotelapp.report_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE hotelapp.report_id_seq OWNER TO postgres;

--
-- TOC entry 5081 (class 0 OID 0)
-- Dependencies: 224
-- Name: report_id_seq; Type: SEQUENCE OWNED BY; Schema: hotelapp; Owner: postgres
--

ALTER SEQUENCE hotelapp.report_id_seq OWNED BY hotelapp.report.id;


--
-- TOC entry 231 (class 1259 OID 16521)
-- Name: rooms; Type: TABLE; Schema: hotelapp; Owner: postgres
--

CREATE TABLE hotelapp.rooms (
    id bigint NOT NULL,
    floor_number integer NOT NULL,
    room_name character varying(255) NOT NULL,
    room_number integer NOT NULL,
    status integer
);


ALTER TABLE hotelapp.rooms OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16520)
-- Name: rooms_id_seq; Type: SEQUENCE; Schema: hotelapp; Owner: postgres
--

CREATE SEQUENCE hotelapp.rooms_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE hotelapp.rooms_id_seq OWNER TO postgres;

--
-- TOC entry 5082 (class 0 OID 0)
-- Dependencies: 230
-- Name: rooms_id_seq; Type: SEQUENCE OWNED BY; Schema: hotelapp; Owner: postgres
--

ALTER SEQUENCE hotelapp.rooms_id_seq OWNED BY hotelapp.rooms.id;


--
-- TOC entry 227 (class 1259 OID 16458)
-- Name: users; Type: TABLE; Schema: hotelapp; Owner: postgres
--

CREATE TABLE hotelapp.users (
    id bigint NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    middle_name character varying(255),
    password character varying(255) NOT NULL,
    status integer,
    user_name character varying(255) NOT NULL
);


ALTER TABLE hotelapp.users OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16457)
-- Name: users_id_seq; Type: SEQUENCE; Schema: hotelapp; Owner: postgres
--

CREATE SEQUENCE hotelapp.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE hotelapp.users_id_seq OWNER TO postgres;

--
-- TOC entry 5083 (class 0 OID 0)
-- Dependencies: 226
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: hotelapp; Owner: postgres
--

ALTER SEQUENCE hotelapp.users_id_seq OWNED BY hotelapp.users.id;


--
-- TOC entry 4882 (class 2604 OID 16545)
-- Name: booking id; Type: DEFAULT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.booking ALTER COLUMN id SET DEFAULT nextval('hotelapp.booking_id_seq'::regclass);


--
-- TOC entry 4883 (class 2604 OID 16546)
-- Name: client id; Type: DEFAULT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.client ALTER COLUMN id SET DEFAULT nextval('hotelapp.client_id_seq'::regclass);


--
-- TOC entry 4886 (class 2604 OID 16547)
-- Name: floors id; Type: DEFAULT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.floors ALTER COLUMN id SET DEFAULT nextval('hotelapp.floors_id_seq'::regclass);


--
-- TOC entry 4884 (class 2604 OID 16548)
-- Name: report id; Type: DEFAULT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.report ALTER COLUMN id SET DEFAULT nextval('hotelapp.report_id_seq'::regclass);


--
-- TOC entry 4887 (class 2604 OID 16549)
-- Name: rooms id; Type: DEFAULT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.rooms ALTER COLUMN id SET DEFAULT nextval('hotelapp.rooms_id_seq'::regclass);


--
-- TOC entry 4885 (class 2604 OID 16550)
-- Name: users id; Type: DEFAULT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.users ALTER COLUMN id SET DEFAULT nextval('hotelapp.users_id_seq'::regclass);


--
-- TOC entry 5062 (class 0 OID 16390)
-- Dependencies: 221
-- Data for Name: booking; Type: TABLE DATA; Schema: hotelapp; Owner: postgres
--

COPY hotelapp.booking (id, booking_date, booking_details, status) FROM stdin;
\.


--
-- TOC entry 5064 (class 0 OID 16398)
-- Dependencies: 223
-- Data for Name: client; Type: TABLE DATA; Schema: hotelapp; Owner: postgres
--

COPY hotelapp.client (id, email, name, status) FROM stdin;
\.


--
-- TOC entry 5070 (class 0 OID 16511)
-- Dependencies: 229
-- Data for Name: floors; Type: TABLE DATA; Schema: hotelapp; Owner: postgres
--

COPY hotelapp.floors (id, floor_name, floor_number, status) FROM stdin;
1	Floor 1	1	1
2	Floor 2	2	1
\.


--
-- TOC entry 5066 (class 0 OID 16408)
-- Dependencies: 225
-- Data for Name: report; Type: TABLE DATA; Schema: hotelapp; Owner: postgres
--

COPY hotelapp.report (id, description, report_name, status) FROM stdin;
\.


--
-- TOC entry 5072 (class 0 OID 16521)
-- Dependencies: 231
-- Data for Name: rooms; Type: TABLE DATA; Schema: hotelapp; Owner: postgres
--

COPY hotelapp.rooms (id, floor_number, room_name, room_number, status) FROM stdin;
1	1	Room 101	101	1
2	2	Room 201	201	1
3	2	Room 202	202	1
4	1	Room 102	102	1
\.


--
-- TOC entry 5068 (class 0 OID 16458)
-- Dependencies: 227
-- Data for Name: users; Type: TABLE DATA; Schema: hotelapp; Owner: postgres
--

COPY hotelapp.users (id, first_name, last_name, middle_name, password, status, user_name) FROM stdin;
1	perumalraj	S	\N	123	1	peru
\.


--
-- TOC entry 5084 (class 0 OID 0)
-- Dependencies: 220
-- Name: booking_id_seq; Type: SEQUENCE SET; Schema: hotelapp; Owner: postgres
--

SELECT pg_catalog.setval('hotelapp.booking_id_seq', 1, false);


--
-- TOC entry 5085 (class 0 OID 0)
-- Dependencies: 222
-- Name: client_id_seq; Type: SEQUENCE SET; Schema: hotelapp; Owner: postgres
--

SELECT pg_catalog.setval('hotelapp.client_id_seq', 1, false);


--
-- TOC entry 5086 (class 0 OID 0)
-- Dependencies: 228
-- Name: floors_id_seq; Type: SEQUENCE SET; Schema: hotelapp; Owner: postgres
--

SELECT pg_catalog.setval('hotelapp.floors_id_seq', 2, true);


--
-- TOC entry 5087 (class 0 OID 0)
-- Dependencies: 224
-- Name: report_id_seq; Type: SEQUENCE SET; Schema: hotelapp; Owner: postgres
--

SELECT pg_catalog.setval('hotelapp.report_id_seq', 1, false);


--
-- TOC entry 5088 (class 0 OID 0)
-- Dependencies: 230
-- Name: rooms_id_seq; Type: SEQUENCE SET; Schema: hotelapp; Owner: postgres
--

SELECT pg_catalog.setval('hotelapp.rooms_id_seq', 4, true);


--
-- TOC entry 5089 (class 0 OID 0)
-- Dependencies: 226
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: hotelapp; Owner: postgres
--

SELECT pg_catalog.setval('hotelapp.users_id_seq', 1, true);


--
-- TOC entry 4889 (class 2606 OID 16396)
-- Name: booking booking_pkey; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.booking
    ADD CONSTRAINT booking_pkey PRIMARY KEY (id);


--
-- TOC entry 4891 (class 2606 OID 16406)
-- Name: client client_pkey; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- TOC entry 4903 (class 2606 OID 16519)
-- Name: floors floors_pkey; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.floors
    ADD CONSTRAINT floors_pkey PRIMARY KEY (id);


--
-- TOC entry 4893 (class 2606 OID 16416)
-- Name: report report_pkey; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.report
    ADD CONSTRAINT report_pkey PRIMARY KEY (id);


--
-- TOC entry 4909 (class 2606 OID 16530)
-- Name: rooms rooms_pkey; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (id);


--
-- TOC entry 4911 (class 2606 OID 16536)
-- Name: rooms uk_36daphag00mnxmwforqperdnb; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.rooms
    ADD CONSTRAINT uk_36daphag00mnxmwforqperdnb UNIQUE (room_name);


--
-- TOC entry 4913 (class 2606 OID 16538)
-- Name: rooms uk_7ljglxlj90ln3lbas4kl983m2; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.rooms
    ADD CONSTRAINT uk_7ljglxlj90ln3lbas4kl983m2 UNIQUE (room_number);


--
-- TOC entry 4895 (class 2606 OID 16472)
-- Name: users uk_8kvt8y8cs0svdi9ucg7ils2ps; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.users
    ADD CONSTRAINT uk_8kvt8y8cs0svdi9ucg7ils2ps UNIQUE (first_name);


--
-- TOC entry 4905 (class 2606 OID 16532)
-- Name: floors uk_efldbpmxgp641uikslgje6hu3; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.floors
    ADD CONSTRAINT uk_efldbpmxgp641uikslgje6hu3 UNIQUE (floor_name);


--
-- TOC entry 4907 (class 2606 OID 16534)
-- Name: floors uk_fmpg25jtvtge5fsnde4085efn; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.floors
    ADD CONSTRAINT uk_fmpg25jtvtge5fsnde4085efn UNIQUE (floor_number);


--
-- TOC entry 4897 (class 2606 OID 16476)
-- Name: users uk_k8d0f2n7n88w1a16yhua64onx; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.users
    ADD CONSTRAINT uk_k8d0f2n7n88w1a16yhua64onx UNIQUE (user_name);


--
-- TOC entry 4899 (class 2606 OID 16474)
-- Name: users uk_pj3y4igathwftoye797s340dn; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.users
    ADD CONSTRAINT uk_pj3y4igathwftoye797s340dn UNIQUE (last_name);


--
-- TOC entry 4901 (class 2606 OID 16470)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: hotelapp; Owner: postgres
--

ALTER TABLE ONLY hotelapp.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


-- Completed on 2025-12-11 19:34:22

--
-- PostgreSQL database dump complete
--

\unrestrict LUWazTmv5B3X226HVDROFJZ2QmJqrG6gbW42DyPzGLPW2Dj5DDhYSLFjxshmP9Y

