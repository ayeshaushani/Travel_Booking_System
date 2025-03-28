package lk.ijse.travel_booking_system.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class webConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
}
}
