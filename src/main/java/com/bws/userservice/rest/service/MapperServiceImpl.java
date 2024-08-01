package com.bws.userservice.rest.service;

import com.bws.userservice.rest.service.interfaces.MapperService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapperServiceImpl implements MapperService {

    private final ModelMapper modelMapper;

    @Override
    public <T, D> List<D> map(List<T> source, Class<D> destination) {
        Assert.notNull(source,"source");
        //TODO ASSERT...
        List<D> target = new ArrayList<>();
        for (T element : source) {
            target.add(modelMapper.map(element, destination));
        }
        return target;
    }

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    @Override
    public void map(Object source, Object destination) {
        modelMapper.map(source, destination);
    }
}
