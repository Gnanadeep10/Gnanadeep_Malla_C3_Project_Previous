import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    Restaurant restaurant;
    Restaurant spyRestaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    LocalTime openingTime;
    LocalTime closingTime;
    int initialMenuSize;

    @BeforeEach
    public void beforeEach() {
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        spyRestaurant = Mockito.spy(restaurant);

        initialMenuSize = restaurant.getMenu().size();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("12:30:00"));
        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("08:30:00"));
        assertFalse(spyRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void calling_order_total_should_return_correct_value_if_items_are_present() throws itemNotFoundException {
        String[] items = {"Sweet corn soup", "Vegetable lasagne"};
        int orderTotal = restaurant.getOrderValue(items);
        assertEquals(388, orderTotal);
    }

    @Test
    public void calling_order_total_should_throw_error_if_any_item_is_not_found() throws itemNotFoundException {
        String[] items = {"Sweet corn soup", "carrot"};
        assertThrows(itemNotFoundException.class,
                () -> restaurant.getOrderValue(items));
    }
}