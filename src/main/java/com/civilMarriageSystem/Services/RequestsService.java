package com.civilMarriageSystem.Services;

import com.civilMarriageSystem.Repositories.RequestsRepository;
import com.civilMarriageSystem.Domain.Requests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestsService {

    @Autowired
    private RequestsRepository repo;

    public List<Requests> listAll(){
        return repo.findAll();
    }

    public void save(Requests request){
        repo.save(request);
    }

    public Requests get(Integer id){
        return repo.findById(id).get();
    }

    public void delete(Integer id){
        repo.deleteById(id);
    }

}
