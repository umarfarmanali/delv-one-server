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
    
-- Table: policy
-- DROP TABLE policy;
CREATE TABLE policy
(
	id integer NOT NULL,
 	name character varying(50) NOT NULL,
	CONSTRAINT policy_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE policy
  OWNER TO delv_one;

-- Table: role
-- DROP TABLE role;
CREATE TABLE role
(
	id integer NOT NULL,
	name character varying NOT NULL,
	CONSTRAINT role_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE role
  OWNER TO delv_one;

-- Table: role_policy
-- DROP TABLE role_policy;
CREATE TABLE role_policy
(
	role_id integer NOT NULL,
	policy_id integer NOT NULL,
	CONSTRAINT role_policy_pkey PRIMARY KEY (role_id, policy_id),
  	CONSTRAINT role_policy_fkey_policy_id FOREIGN KEY (policy_id)
      REFERENCES policy (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  	CONSTRAINT role_policy_fkey_role_id FOREIGN KEY (role_id)
      REFERENCES role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE role_policy
  OWNER TO delv_one;
  
-- Table: user_role
-- DROP TABLE user_role;
CREATE TABLE user_role
(
  	user_info_id integer NOT NULL,
  	role_id integer NOT NULL,
  	CONSTRAINT user_role_pkey PRIMARY KEY (user_info_id, role_id),
  	CONSTRAINT user_role_fkey_role_id FOREIGN KEY (role_id)
      REFERENCES role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  	CONSTRAINT user_role_fkey_user_info_id FOREIGN KEY (user_info_id)
      REFERENCES user_info (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_role
  OWNER TO delv_one;
