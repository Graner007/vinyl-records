/*ALTER TABLE IF EXISTS ONLY public.product DROP CONSTRAINT IF EXISTS fk_genre_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.product DROP CONSTRAINT IF EXISTS fk_artist_id CASCADE;*/

DROP TABLE IF EXISTS public.product;
CREATE TABLE public.product (
                                id serial NOT NULL PRIMARY KEY,
                                name text NOT NULL,
                                description text NOT NULL,
                                defaultPrice float NOT NULL,
                                defaultCurrency text NOT NULL,
                                genre_id integer NOT NULL,
                                artist_id integer NOT NULL
);

DROP TABLE IF EXISTS public.genre;
CREATE TABLE public.genre (
                              id serial NOT NULL PRIMARY KEY,
                              name text NOT NULL,
                              description text NOT NULL
);

DROP TABLE IF EXISTS public.artist;
CREATE TABLE public.artist (
                               id serial NOT NULL PRIMARY KEY,
                               name text NOT NULL,
                               description text NOT NULL
);

/*ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_genre_id FOREIGN KEY (genre_id) REFERENCES public.genre(id);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_artist_id FOREIGN KEY (artist_id) REFERENCES public.artist(id);*/