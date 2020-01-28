-- Table: customer_type
-- DROP TABLE customer_type;
CREATE TABLE public.customer_type
(
  type integer NOT NULL,
  CONSTRAINT customer_type_pkey PRIMARY KEY (type)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.customer_type
  OWNER TO delv_one;


-- Table: public.user_info
-- DROP TABLE public.user_info;
CREATE TABLE public.user_info
(
    id bigint NOT NULL DEFAULT nextval('user_info_id_seq'::regclass),
    username character varying(25) COLLATE pg_catalog."default" NOT NULL,
    email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password bytea NOT NULL,
    salt bytea NOT NULL,
    active integer NOT NULL,
    CONSTRAINT user_info_pkey PRIMARY KEY (id),
    CONSTRAINT user_info_ukey_username UNIQUE (username),
    CONSTRAINT user_info_ukey_email UNIQUE (email)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.user_info
    OWNER to delv_one;