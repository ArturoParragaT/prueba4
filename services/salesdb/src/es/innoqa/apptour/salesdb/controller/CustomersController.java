/*Copyright (c) 2021-2022 innoqa.es All Rights Reserved.
 This software is the confidential and proprietary information of innoqa.es You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with innoqa.es*/
package es.innoqa.apptour.salesdb.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.commons.file.manager.ExportedFileManager;
import com.wavemaker.runtime.commons.file.model.Downloadable;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.tools.api.core.annotations.MapTo;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import es.innoqa.apptour.salesdb.Customers;
import es.innoqa.apptour.salesdb.Leads;
import es.innoqa.apptour.salesdb.service.CustomersService;


/**
 * Controller object for domain model class Customers.
 * @see Customers
 */
@RestController("salesdb.CustomersController")
@Api(value = "CustomersController", description = "Exposes APIs to work with Customers resource.")
@RequestMapping("/salesdb/Customers")
public class CustomersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomersController.class);

    @Autowired
	@Qualifier("salesdb.CustomersService")
	private CustomersService customersService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new Customers instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Customers createCustomers(@RequestBody Customers customers) {
		LOGGER.debug("Create Customers with information: {}" , customers);

		customers = customersService.create(customers);
		LOGGER.debug("Created Customers with information: {}" , customers);

	    return customers;
	}

    @ApiOperation(value = "Returns the Customers instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Customers getCustomers(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting Customers with id: {}" , id);

        Customers foundCustomers = customersService.getById(id);
        LOGGER.debug("Customers details with id: {}" , foundCustomers);

        return foundCustomers;
    }

    @ApiOperation(value = "Updates the Customers instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Customers editCustomers(@PathVariable("id") Integer id, @RequestBody Customers customers) {
        LOGGER.debug("Editing Customers with id: {}" , customers.getId());

        customers.setId(id);
        customers = customersService.update(customers);
        LOGGER.debug("Customers details with id: {}" , customers);

        return customers;
    }
    
    @ApiOperation(value = "Partially updates the Customers instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Customers patchCustomers(@PathVariable("id") Integer id, @RequestBody @MapTo(Customers.class) Map<String, Object> customersPatch) {
        LOGGER.debug("Partially updating Customers with id: {}" , id);

        Customers customers = customersService.partialUpdate(id, customersPatch);
        LOGGER.debug("Customers details after partial update: {}" , customers);

        return customers;
    }

    @ApiOperation(value = "Deletes the Customers instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteCustomers(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting Customers with id: {}" , id);

        Customers deletedCustomers = customersService.delete(id);

        return deletedCustomers != null;
    }

    /**
     * @deprecated Use {@link #findCustomers(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Customers instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Customers> searchCustomersByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Customers list by query filter:{}", (Object) queryFilters);
        return customersService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Customers instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Customers> findCustomers(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Customers list by filter:", query);
        return customersService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Customers instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Customers> filterCustomers(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Customers list by filter", query);
        return customersService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportCustomers(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return customersService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportCustomersAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = Customers.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> customersService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of Customers instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countCustomers( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Customers");
		return customersService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getCustomersAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return customersService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{id:.+}/leadses", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the leadses instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Leads> findAssociatedLeadses(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated leadses");
        return customersService.findAssociatedLeadses(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service CustomersService instance
	 */
	protected void setCustomersService(CustomersService service) {
		this.customersService = service;
	}

}
