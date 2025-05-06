package com.app.Service;

import com.app.Exceptions.NegativeWeightException;
import com.app.Model.Container;
import com.app.Repository.ContainerRepository;
import com.app.Repository.ShipRepository;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

 /**
 * The @Transactional annotation wraps every method in this class inside a database transaction, which is a set of
 * statements that occur in isolation and are applied in an all-or-nothing manner. This helps prevent issues such as
 * dirty reads when a transaction is still in progress.
 *
 * For example, when a transaction includes multiple steps—like processing 100 items in a shopping cart—we don't want
 * an external request to read an incomplete state. If something fails midway, the entire transaction should roll back.
 *
 * @Transactional is implemented using Spring AOP (Aspect-Oriented Programming), which applies transaction logic to
 * all methods in this class.
 * 
 * This can be tested by attempting to persist a list of containers where at least one has a negative weight.
 * If any container has a negative or zero weight, a NegativeWeightException is thrown, and the transaction
 * is rolled back. As a result, no containers will be persisted.
 */
@Transactional(rollbackFor = NegativeWeightException.class)
@Service
public class ContainerService {
    ContainerRepository containerRepository;
    ShipRepository shipRepository;
    // @Autowired is not required if there's only one constructor in the class
    public ContainerService(ContainerRepository containerRepository, ShipRepository shipRepository){
        this.containerRepository = containerRepository;
        this.shipRepository = shipRepository;
    }

    /**
     * Saves a list of container entities.
     * Note: This is a simplistic implementation. Typically, the .saveAll method with a database-level CHECK constraint would be used.
     *
     * @param containers transient container entities
     * @return list of persisted containers
     * @throws NegativeWeightException if any container has non-positive weight
     */
    public List<Container> addListContainers(List<Container> containers) throws NegativeWeightException {
        List<Container> persistedContainers = new ArrayList<>();
        for(int i = 0; i < containers.size(); i++){
            if(containers.get(i).getWeight()<=0){
                throw new NegativeWeightException();
            }
            Container newContainer = containerRepository.save(containers.get(i));
            persistedContainers.add(newContainer);
        }
        return persistedContainers;
    }

    /**
     * Retrieves all container entities.
     *
     * @return list of containers
     */
    public List<Container> getAllContainers() {
        return containerRepository.findAll();
    }

    /**
     * Retrieves a container by ID.
     *
     * @param id container ID
     * @return container entity
     */
    public Container getContainerById(long id) {
        return containerRepository.findById(id).get();
    }
}