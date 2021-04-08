package com.codecool.shop.config;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.mem.*;
import com.codecool.shop.model.Artist;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ConfigType;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.service.ProductServiceController;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //setting up artists
        Artist eminem = new Artist("Eminem", "American rapper, songwriter, and record producer.");
        Artist elvisPresley = new Artist("Elvis Presley", "American rock and roll singer and actor");
        Artist iceT = new Artist("Ice T", "American rapper, actor, songwriter, and producer.");
        Artist ozzyOsborne = new Artist("Ozzy Osborne", "English singer, songwriter, and television personality.");
        Artist burningWitches = new Artist("Burning Witches", "Swiss/Dutch Heavy Metal band.");
        Artist duaLipa = new Artist("Dua Lipa", "English singer and song writer.");
        Artist arianaGrande = new Artist("Ariana Grande", "American singer and actress.");
        Artist tonyAllen = new Artist("Tony Allen", "Nigerian drummer, composer, and songwriter.");
        Artist nubiyanTwist = new Artist("Nubiyan Twist", "Nubiyan Twist are a twelve-piece outfit based in Leeds/London, UK.");

        //setting up a new product category
        Genre hiphop = new Genre("HipHop", "HipHop", "HipHop Music by different artists.");
        Genre metal = new Genre("Metal", "Metal", "Metal music by different artists.");
        Genre pop = new Genre("Pop", "Pop", "Pop music by different artists.");
        Genre rockAndRoll = new Genre("Rock and Roll", "Rock and Roll", "Rock and roll music by different artists.");
        Genre jazz = new Genre("Jazz", "Jazz", "Jazz music by different artists.");

        //setting up products
        Product elvisIsBack = new Product("Elvis Presley", 9.9f, "USD", "Elvis is Back!", rockAndRoll, elvisPresley);
        Product theIceberg = new Product("Ice T", 11.9f, "USD", "The Iceberg", hiphop, iceT);
        Product theMarshallMathersLP2 = new Product("Eminem", 14.9f, "USD", "The Marshall Mathers LP2", hiphop, eminem);
        Product blizzardOfOz = new Product("Ozzy Osborne", 9.9f, "USD", "Blizzard Of Oz", metal, ozzyOsborne);
        Product theWitchOfTheNorth = new Product("Burning Witches", 14.9f, "USD", "The Witch Of The North", metal, burningWitches);
        Product futureNostalgia = new Product("Dua Lipa", 9.9f, "USD", "Future Nostalgia", pop, duaLipa);
        Product positions = new Product("Ariana Grande", 11.9f, "USD", "Positions", pop, arianaGrande);
        Product thereIsNoEnd = new Product("Tony Allen", 11.9f, "USD", "There Is No End", jazz, tonyAllen);
        Product freedomFables = new Product("Nubiyan Twist", 13.9f, "USD", "Freedom Fables", jazz, nubiyanTwist);

        try {
            ProductServiceController productServiceController = new ProductServiceController();
            String dao = ConfigType.getDao();
            if (dao.equals("memory")) {
                ProductService service = productServiceController.getProductService();

                service.addArtist(eminem);
                service.addArtist(elvisPresley);
                service.addArtist(iceT);
                service.addArtist(ozzyOsborne);
                service.addArtist(burningWitches);
                service.addArtist(duaLipa);
                service.addArtist(arianaGrande);
                service.addArtist(tonyAllen);
                service.addArtist(nubiyanTwist);

                service.addGenre(hiphop);
                service.addGenre(pop);
                service.addGenre(metal);
                service.addGenre(rockAndRoll);
                service.addGenre(jazz);

                service.addProduct(elvisIsBack);
                service.addProduct(theIceberg);
                service.addProduct(theMarshallMathersLP2);
                service.addProduct(blizzardOfOz);
                service.addProduct(theWitchOfTheNorth);
                service.addProduct(futureNostalgia);
                service.addProduct(positions);
                service.addProduct(thereIsNoEnd);
                service.addProduct(freedomFables);
            }

            OrderDao orderDataStore = OrderDaoMem.getInstance();
            orderDataStore.add(new Order());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}