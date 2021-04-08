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

INSERT INTO product VALUES (DEFAULT, 'Elvis Presley', 'Elvis is Back!', 9.9, 'USD', 4, 2),
                           (DEFAULT, 'Eminem', 'Kamikaze', 14.9, 'USD', 1, 1),
                           (DEFAULT, 'Eminem', 'The Marshall Mathers LP2', 14.9, 'USD', 1, 1),
                           (DEFAULT, 'Ice T','The Iceberg', 11.9, 'USD', 1, 3),
                           (DEFAULT, 'Ozzy Osborne', 'Blizzard Of Oz', 9.9, 'USD', 2, 4),
                           (DEFAULT, 'Burning Witches', 'The Witch Of The North', 14.9, 'USD', 2, 5),
                           (DEFAULT, 'Dua Lipa', 'Future Nostalgia', 9.9, 'USD', 3, 6),
                           (DEFAULT, 'Ariana Grande', 'Positions', 11.9, 'USD', 3, 7),
                           (DEFAULT, 'Tony Alles', 'There Is No End', 11.9, 'USD', 5, 8),
                           (DEFAULT, 'Nubiyan Twist', 'Freedom Fables', 13.9, 'USD', 5, 9);


DROP TABLE IF EXISTS public.genre;
CREATE TABLE public.genre (
                              id serial NOT NULL PRIMARY KEY,
                              name text NOT NULL,
                              description text NOT NULL
);

INSERT INTO genre VALUES (DEFAULT, 'HipHop', 'HipHop music by different artists.'),
                         (DEFAULT, 'Metal', 'Metal music by different artists.'),
                         (DEFAULT, 'Pop', 'Pop music by different artists.'),
                         (DEFAULT, 'Rock and Roll', 'Rock and Roll music by different artists.'),
                         (DEFAULT, 'Jazz', 'Jazz Music by different artists.');


DROP TABLE IF EXISTS public.artist;
CREATE TABLE public.artist (
                               id serial NOT NULL PRIMARY KEY,
                               name text NOT NULL,
                               description text NOT NULL
);

DROP TABLE IF EXISTS public.user;
CREATE TABLE public.user (
                               id serial NOT NULL PRIMARY KEY,
                               name text NOT NULL,
                               email text NOT NULL,
                               password text NOT NULL,
                               phoneNumber text DEFAULT NULL,
                               billingAddress text DEFAULT NULL,
                               billingCity text DEFAULT NULL,
                               billingState text DEFAULT NULL,
                               billingZip integer DEFAULT NULL,
                               shoppingAddress text DEFAULT NULL,

);

INSERT INTO artist VALUES (DEFAULT, 'Eminem', 'American rapper, songwriter, and record producer.'),
                          (DEFAULT, 'Elvis Presley', 'American rock and roll singer and actor.'),
                          (DEFAULT, 'Ice T', 'American rapper, actor, songwriter, and producer.'),
                          (DEFAULT, 'Ozzy Osborne', 'English singer, songwriter, and television personality.'),
                          (DEFAULT, 'Burning Witches', 'Swiss/Dutch Heavy Metal band.'),
                          (DEFAULT, 'Dua Lipa', 'English singer and song writer.'),
                          (DEFAULT, 'Ariana Grande', 'American singer and actress.'),
                          (DEFAULT, 'Tony Allen', 'Nigerian drummer, composer, and songwriter.'),
                          (DEFAULT, 'Nubiyan Twist', 'Nubiyan Twist are a twelve-piece outfit based in Leeds/London, UK.');
