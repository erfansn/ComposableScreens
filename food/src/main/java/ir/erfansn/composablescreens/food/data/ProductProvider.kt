package ir.erfansn.composablescreens.food.data

import androidx.compose.ui.graphics.Color
import ir.erfansn.composablescreens.food.R

object ProductProvider {

    fun getProductList(): List<Product> {
        return listOf(
            Product(
                id = 0,
                title = "Peanut Butter Chocolate Chip Cookie",
                imageId = R.drawable.peanut_butter_chocolate_chip_cookie,
                backgroundColor = Color(0xFFFCE798),
                priceInCent = 600,
                ingredients = listOf(
                    "Organic Flour",
                    "Organic Cane Sugar",
                    "Organic Peanut Butter",
                    "Organic Vanilla",
                    "Non Gmo Baking Powder",
                    "Sea Salt",
                    "Backing Soda",
                    "Butter",
                    "Dark Chocolate",
                    "Eggs",
                    "Milk Chocolate",
                    "Peanut Powder"
                ),
                description = "One bite into our chocolate chip peanut butter cookie and you’ll be insane with cravings for more. These mouthwatering peanut butter cookies with chocolate chips are a creamy hit with the ladies and fellas alike. The salty accents complement the chocolaty sweet bits, and when you chomp into one of these comely creations, the blend with send you into a fully loco state of euphoria."
            ),
            Product(
                id = 1,
                title = "Sea Salt Chocolate Chip Cookie",
                imageId = R.drawable.sea_salt_chocolate_chip_cookie,
                backgroundColor = Color(0xFFE4F9CD),
                priceInCent = 800,
                ingredients = listOf(
                    "Organic Brown Sugar",
                    "Organic Cane Sugar",
                    "Organic Corn Starch",
                    "Organic Vanilla",
                    "Non Gmo Baking Powder",
                    "Sea Salt",
                    "Backing Soda",
                    "Butter",
                    "Dark Chocolate",
                    "Eggs",
                    "Milk Chocolate",
                    "Organic Flour"
                ),
                description = "Imagine what it feels like when you bite into one of our absolute best chocolate cookie with sea salt. This incredibly delicious, charming little number will get your taste buds watering! Enjoy these yummy, chewy chocolate chip cookies at home, work, or even the beach for an authentically briny experience."
            ),
            Product(
                id = 2,
                title = "Triple Chocolate Chip Cookie",
                imageId = R.drawable.triple_chocolate_cookie,
                backgroundColor = Color(0xFFEBE0FE),
                priceInCent = 399,
                ingredients = listOf(
                    "Organic Flour",
                    "Organic Cane Sugar",
                    "Organic Cocoa Powder",
                    "Organic Corn Starch",
                    "Organic Vanilla",
                    "Non Gmo Baking Powder",
                    "Sea Salt",
                    "Backing Soda",
                    "Butter",
                    "Dark Chocolate",
                    "Eggs",
                    "Milk Chocolate"
                ),
                description = "Three Chocolates, one Cookie. Our triple chocolate chip cookie will fill you up with three times the dangerously decadent deliciousness of any other. Chewy, dark, and handsome, this chocolate chocolate chip cookie will take you to new heights of cocoa ecstasy. Double-entendres aside, chocolate lovers will be twice as satisfied."
            ),
            Product(
                id = 3,
                title = "White Chocolate Macadamia Nut Cookie",
                imageId = R.drawable.white_chocolate_macadamia_nut_cookie,
                backgroundColor = Color(0xFFDDEAFE),
                priceInCent = 440,
                ingredients = listOf(
                    "Organic Flour",
                    "Organic Cane Sugar",
                    "Macadamia Nuts",
                    "Organic Corn Starch",
                    "Organic Vanilla",
                    "Non Gmo Baking Powder",
                    "Sea Salt",
                    "Backing Soda",
                    "Butter",
                    "Eggs",
                    "White Chocolate"
                ),
                description = "You can always eat white, even after Labor Day. Our wholesome yet slightly wicked white chocolate macadamia cookie is the perfectly alluring antidote to quell your hunger. Reliably chewy, these soft white chocolate macadamia nut cookies are the  stuff. Made with deceptively sweet white chocolate chips and humungo macadamia nuts, these are the best white chocolate macadamia nut cookie on the scene. Just one bite, and you’ll be licking the crumbs off the plate."
            ),
            Product(
                id = 4,
                title = "Snickerchurro Cookie",
                imageId = R.drawable.snickerchurro_cookie,
                backgroundColor = Color(0xFFFEEAE3),
                priceInCent = 550,
                ingredients = listOf(
                    "Organic Flour",
                    "Organic Cane Sugar",
                    "Organic Brown Sugar",
                    "Organic Corn Starch",
                    "Dulce De Leche",
                    "Organic Vanilla",
                    "Organic Cinnamon",
                    "Sea Salt",
                    "Non Gmo Baking Powder",
                    "Backing Soda",
                    "Butter",
                    "Eggs"
                ),
                description = "Don’t you dare snicker at this delicious delicacy. The heretofore unheard of blend of organic cinnamon, brown sugar, and dulce de leche makes for one zesty batch of fresh-baked divinity. Indeed, this snickerdoodle cookie stuffed with dulce de leche will have you screaming at their very existence. Snickerdoodle + Dulce De Leche = Snickerchurro."
            ),
            Product(
                id = 5,
                title = "S'mores Cookie",
                imageId = R.drawable.s_mores_cookie,
                backgroundColor = Color(0xFFFCE798),
                priceInCent = 550,
                ingredients = listOf(
                    "Organic Flour",
                    "Organic Cane Sugar",
                    "Organic Brown Sugar",
                    "Organic Graham Crackers",
                    "Organic Corn Starch",
                    "Organic Vanilla",
                    "Sea Salt",
                    "Non Gmo Baking Powder",
                    "Backing Soda",
                    "Butter",
                    "Dark Chocolate",
                    "Eggs",
                    "Marshmallow Creme"
                ),
                description = "Remember sitting around a roaring campfire, roasting marshmallows, and telling tales of yore? Well, these smackingly succulent s’mores cookies are just like that, without all the smoke in your hair and having to listen to lame stories. The Bang Cookies S’mores Cookie is the perfect blend of crunchy graham crackers, rich dark chocolate, and mushy marshmallow glee. A throwback to simpler times..."
            ),
            Product(
                id = 6,
                title = "Chocolate Peanut Butter Stuffed Cookie",
                imageId = R.drawable.chocolate_peanut_butter_stuffed_cookie,
                backgroundColor = Color(0xFFE4F9CD),
                priceInCent = 550,
                ingredients = listOf(
                    "Backing Soda",
                    "Butter",
                    "Eggs",
                    "Non Gmo Baking Powder",
                    "Organic Brown Sugar",
                    "Organic Cane Sugar",
                    "Organic Cocoa Powder",
                    "Organic Corn Starch",
                    "Organic Flour",
                    "Organic Vanilla Extract",
                    "Sea Salt",
                ),
                description = ""
            ),
            Product(
                id = 7,
                title = "Oatmeal Raisin Cookie",
                imageId = R.drawable.oatmeal_raisin_cookie,
                backgroundColor = Color(0xFFDDEAFE),
                priceInCent = 590,
                ingredients = listOf(
                    "Organic Flour",
                    "Organic Cane Sugar",
                    "Organic Brown Sugar",
                    "Organic Oats",
                    "Organic Raisins",
                    "Organic Corn Starch",
                    "Organic Vanilla",
                    "Organic Cinnamon",
                    "Sea Salt",
                    "Non Gmo Baking Powder",
                    "Backing Soda",
                    "Butter",
                    "Eggs",
                ),
                description = "This ain’t your gramma’s oatmeal raisin cookie. The undeniable gem of our collection, this audaciously scrumptious, yet maturely sophisticated cookie has got it all. With plump organic raisins, spirited cinnamon, and rich brown sugar, these are the best oatmeal raisin cookies you’re ever gonna sink your ravenous teeth into."
            ),
            Product(
                id = 8,
                title = "Caramel Sea Salt Cookie",
                imageId = R.drawable.caramel_sea_salt_cookie,
                backgroundColor = Color(0xFFFEEAE3),
                priceInCent = 550,
                ingredients = listOf(
                    "Organic Flour",
                    "Organic Cane Sugar",
                    "Organic Brown Sugar",
                    "Organic Corn Starch",
                    "Organic Vanilla",
                    "Sea Salt",
                    "Non Gmo Baking Powder",
                    "Backing Soda",
                    "Butter",
                    "Dulce De Leche",
                    "Eggs",
                    "Milk Chocolate",
                ),
                description = "There’s just something attractive about a cookie with a secret. On the outside, these perfectly cute and modestly attired caramel cookies appear as if they are just the friendly gal next door. But, when you take off the salted caramel cookie layer and take a peek inside, you'll see an ocean of flirtatiously flowing caramel. You’ll literally be powerless to resist these chewy salted caramel cookies, which combine the sweet and the briny, creating a magical medley of alluring, treasured taste."
            )
        )
    }
}
