//package cz.muni.fi.pa165.service.converter;
//
//
//import cz.muni.fi.pa165.dto.MovieDto;
//import cz.muni.fi.pa165.entity.Movie;
//import ma.glasnost.orika.MapperFacade;
//import ma.glasnost.orika.MapperFactory;
//import ma.glasnost.orika.impl.DefaultMapperFactory;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.inject.Named;
//
///**
// * @author juraj
// */
//
//@Component
//public class OrikaConverter implements BeanConverter{
//    private MapperFacade mapperFacade;
//
//    public OrikaConverter() {
//        MapperFactory mapperFactory = new DefaultMapperFactory
//                .Builder().build();
//
//        mapperFactory.classMap(Movie.class, MovieDto.class)
//                .field("name", "name").byDefault().register();
//        mapperFacade = mapperFactory.getMapperFacade();
//    }
//
//
//    @Override
//    public <T, F> T convert(F from, Class<T> to) {
//        return mapperFacade.map(from, to);
//    }
//}