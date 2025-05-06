package com.app.Service;

import com.app.Exceptions.InvalidTonnageException;
import com.app.Model.Ship;
import com.app.Repository.ShipRepository;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The @Transactional annotation wraps all methods in this service in a database transaction.
 * If an InvalidTonnageException is thrown, the transaction will be rolled back.
 *
 * This prevents partial persistence of ship records when one or more entries are invalid (e.g., with non-positive tonnage).
 *
 * This behavior can be verified by attempting to persist a list of ships that includes one invalid entry,
 * confirming that none are saved.
 */
@Service
@Transactional(rollbackFor = InvalidTonnageException.class)
public class ShipService {
    private final ShipRepository shipRepository;
    // @Autowired is not required if there's only one constructor in the class
    public ShipService(ShipRepository shipRepository){
        this.shipRepository = shipRepository;
    }

    /**
     * Saves a list of ship entities.
     * Note: Normally you would use the .saveAll method with a DB-level CHECK constraint.
     *
     * @param ships transient ship entities
     * @return list of persisted ships
     * @throws InvalidTonnageException if any ship has non-positive tonnage
     */
    public List<Ship> addListShips(List<Ship> ships) throws InvalidTonnageException {
        List<Ship> persistedShips = new ArrayList<>();
        for(int i = 0; i < ships.size(); i++){
            if(ships.get(i).getTonnage()<=0){
                throw new InvalidTonnageException();
            }
            persistedShips.add(shipRepository.save(ships.get(i)));
        }
        return persistedShips;
    }

    /**
     * Retrieves all ship entities.
     *
     * @return list of ships
     */
    public List<Ship> getAllShips() {
        return shipRepository.findAll();
    }

    /**
     * Retrieves a ship by ID.
     *
     * @param id ship ID
     * @return ship entity
     */
    public Ship getShipById(long id) {
        return shipRepository.findById(id).get();
    }
}