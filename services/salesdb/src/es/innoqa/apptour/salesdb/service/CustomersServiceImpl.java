/*Copyright (c) 2021-2022 innoqa.es All Rights Reserved.
 This software is the confidential and proprietary information of innoqa.es You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with innoqa.es*/
package es.innoqa.apptour.salesdb.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.commons.file.model.Downloadable;
import com.wavemaker.runtime.data.annotations.EntityService;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;

import es.innoqa.apptour.salesdb.Customers;
import es.innoqa.apptour.salesdb.Leads;


/**
 * ServiceImpl object for domain model class Customers.
 *
 * @see Customers
 */
@Service("salesdb.CustomersService")
@Validated
@EntityService(entityClass = Customers.class, serviceId = "salesdb")
public class CustomersServiceImpl implements CustomersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomersServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("salesdb.LeadsService")
    private LeadsService leadsService;

    @Autowired
    @Qualifier("salesdb.CustomersDao")
    private WMGenericDao<Customers, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<Customers, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "salesdbTransactionManager")
    @Override
    public Customers create(Customers customers) {
        LOGGER.debug("Creating a new Customers with information: {}", customers);

        Customers customersCreated = this.wmGenericDao.create(customers);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(customersCreated);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public Customers getById(Integer customersId) {
        LOGGER.debug("Finding Customers by id: {}", customersId);
        return this.wmGenericDao.findById(customersId);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public Customers findById(Integer customersId) {
        LOGGER.debug("Finding Customers by id: {}", customersId);
        try {
            return this.wmGenericDao.findById(customersId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Customers found with id: {}", customersId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public List<Customers> findByMultipleIds(List<Integer> customersIds, boolean orderedReturn) {
        LOGGER.debug("Finding Customers by ids: {}", customersIds);

        return this.wmGenericDao.findByMultipleIds(customersIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "salesdbTransactionManager")
    @Override
    public Customers update(Customers customers) {
        LOGGER.debug("Updating Customers with information: {}", customers);

        this.wmGenericDao.update(customers);
        this.wmGenericDao.refresh(customers);

        return customers;
    }

    @Transactional(value = "salesdbTransactionManager")
    @Override
    public Customers partialUpdate(Integer customersId, Map<String, Object>customersPatch) {
        LOGGER.debug("Partially Updating the Customers with id: {}", customersId);

        Customers customers = getById(customersId);

        try {
            ObjectReader customersReader = this.objectMapper.reader().forType(Customers.class).withValueToUpdate(customers);
            customers = customersReader.readValue(this.objectMapper.writeValueAsString(customersPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", customersPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        customers = update(customers);

        return customers;
    }

    @Transactional(value = "salesdbTransactionManager")
    @Override
    public Customers delete(Integer customersId) {
        LOGGER.debug("Deleting Customers with id: {}", customersId);
        Customers deleted = this.wmGenericDao.findById(customersId);
        if (deleted == null) {
            LOGGER.debug("No Customers found with id: {}", customersId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Customers.class.getSimpleName(), customersId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "salesdbTransactionManager")
    @Override
    public void delete(Customers customers) {
        LOGGER.debug("Deleting Customers with {}", customers);
        this.wmGenericDao.delete(customers);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public Page<Customers> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Customers");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public Page<Customers> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Customers");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service salesdb for table Customers to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service salesdb for table Customers to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public Page<Leads> findAssociatedLeadses(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated leadses");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("customers.id = '" + id + "'");

        return leadsService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service LeadsService instance
     */
    protected void setLeadsService(LeadsService service) {
        this.leadsService = service;
    }

}
