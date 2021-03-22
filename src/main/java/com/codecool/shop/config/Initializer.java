package com.codecool.shop.config;

import com.codecool.shop.dao.GenreDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ArtistDao;
import com.codecool.shop.dao.implementation.GenreDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ArtistDaoMem;
import com.codecool.shop.model.Artist;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.Product;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        GenreDao productCategoryDataStore = GenreDaoMem.getInstance();
        ArtistDao supplierDataStore = ArtistDaoMem.getInstance();

        //setting up a new artist
        Artist eminem = new Artist("Eminem", "American rapper, songwriter, and record producer.");
        supplierDataStore.add(eminem);
        Artist iceT = new Artist("Ice T", "American rapper, actor, songwriter, and producer.");
        supplierDataStore.add(iceT);
        Artist ozzyOsborne = new Artist("Ozzy Osborne", "English singer, songwriter, and television personality.");
        supplierDataStore.add(ozzyOsborne);
        Artist burningWitches = new Artist("Burning Witches", "Swiss/Dutch Heavy Metal band.");
        supplierDataStore.add(burningWitches);
        Artist duaLipa = new Artist("Dua Lipa", "English singer and song writer.");
        supplierDataStore.add(duaLipa);
        Artist arianaGrande = new Artist("Ariana Grande", "American singer and actress.");
        supplierDataStore.add(arianaGrande);
        Artist tonyAllen = new Artist("Tony Allen", "Nigerian drummer, composer, and songwriter.");
        supplierDataStore.add(tonyAllen);
        Artist nubiyanTwist = new Artist("Nubiyan Twist", "Nubiyan Twist are a twelve-piece outfit based in Leeds/London, UK.");
        supplierDataStore.add(nubiyanTwist);


        //setting up a new product category
        Genre hiphop = new Genre("HipHop", "HipHop", "HipHop Music by different artists.");
        productCategoryDataStore.add(hiphop);
        Genre metal = new Genre("Metal", "Metal", "Metal music by different artists.");
        productCategoryDataStore.add(metal);
        Genre pop = new Genre("Pop", "Pop", "Pop music by different artists.");
        productCategoryDataStore.add(pop);
        Genre jazz = new Genre("Jazz", "Jazz", "Jazz music by different artists.");
        productCategoryDataStore.add(jazz);

        //setting up products and printing it
        productDataStore.add(new Product("Eminem", 9.9f, "USD", "Kamikaze", hiphop, eminem));
        productDataStore.add(new Product("Ice T", 11.9f, "USD", "The Iceberg", hiphop, iceT));
        productDataStore.add(new Product("Eminem", 14.9f, "USD", "The Marshall Mathers LP2", hiphop, eminem));
        productDataStore.add(new Product("Ozzy Osborne", 9.9f, "USD", "Blizzard Of Oz", metal, ozzyOsborne));
        productDataStore.add(new Product("Burning Witches", 14.9f, "USD", "The Witch Of The North", metal, burningWitches));
        productDataStore.add(new Product("Dua Lipa", 9.9f, "USD", "Future Nostalgia", pop, duaLipa));
        productDataStore.add(new Product("Ariana Grande", 11.9f, "USD", "Positions", pop, arianaGrande));
        productDataStore.add(new Product("Tony Allen", 11.9f, "USD", "There Is No End", jazz, tonyAllen));
        productDataStore.add(new Product("Nubiyan Twist", 13.9f, "USD", "Freedom Fables", jazz, nubiyanTwist));
    }
}
