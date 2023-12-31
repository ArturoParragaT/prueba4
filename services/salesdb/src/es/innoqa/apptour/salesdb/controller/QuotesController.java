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

import es.innoqa.apptour.salesdb.FollowUps;
import es.innoqa.apptour.salesdb.Quotes;
import es.innoqa.apptour.salesdb.Sales;
import es.innoqa.apptour.salesdb.service.QuotesService;


/**
 * Controller object for domain model class Quotes.
 * @see Quotes
 */
@RestController("salesdb.QuotesController")
@Api(value = "QuotesController", description = "Exposes APIs to work with Quotes resource.")
@RequestMapping("/salesdb/Quotes")
public class QuotesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuotesController.class);

    @Autowired
	@Qualifier("salesdb.QuotesService")
	private QuotesService quotesService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new Quotes instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Quotes createQuotes(@RequestBody Quotes quotes) {
		LOGGER.debug("Create Quotes with information: {}" , quotes);

		quotes = quotesService.create(quotes);
		LOGGER.debug("Created Quotes with information: {}" , quotes);

	    return quotes;
	}

    @ApiOperation(value = "Returns the Quotes instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Quotes getQuotes(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting Quotes with id: {}" , id);

        Quotes foundQuotes = quotesService.getById(id);
        LOGGER.debug("Quotes details with id: {}" , foundQuotes);

        return foundQuotes;
    }

    @ApiOperation(value = "Updates the Quotes instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Quotes editQuotes(@PathVariable("id") Integer id, @RequestBody Quotes quotes) {
        LOGGER.debug("Editing Quotes with id: {}" , quotes.getId());

        quotes.setId(id);
        quotes = quotesService.update(quotes);
        LOGGER.debug("Quotes details with id: {}" , quotes);

        return quotes;
    }
    
    @ApiOperation(value = "Partially updates the Quotes instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Quotes patchQuotes(@PathVariable("id") Integer id, @RequestBody @MapTo(Quotes.class) Map<String, Object> quotesPatch) {
        LOGGER.debug("Partially updating Quotes with id: {}" , id);

        Quotes quotes = quotesService.partialUpdate(id, quotesPatch);
        LOGGER.debug("Quotes details after partial update: {}" , quotes);

        return quotes;
    }

    @ApiOperation(value = "Deletes the Quotes instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteQuotes(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting Quotes with id: {}" , id);

        Quotes deletedQuotes = quotesService.delete(id);

        return deletedQuotes != null;
    }

    /**
     * @deprecated Use {@link #findQuotes(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Quotes instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Quotes> searchQuotesByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Quotes list by query filter:{}", (Object) queryFilters);
        return quotesService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Quotes instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Quotes> findQuotes(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Quotes list by filter:", query);
        return quotesService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Quotes instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Quotes> filterQuotes(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Quotes list by filter", query);
        return quotesService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportQuotes(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return quotesService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportQuotesAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = Quotes.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> quotesService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of Quotes instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countQuotes( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Quotes");
		return quotesService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getQuotesAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return quotesService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{id:.+}/followUpses", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the followUpses instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<FollowUps> findAssociatedFollowUpses(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated followUpses");
        return quotesService.findAssociatedFollowUpses(id, pageable);
    }

    @RequestMapping(value="/{id:.+}/saleses", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the saleses instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Sales> findAssociatedSaleses(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated saleses");
        return quotesService.findAssociatedSaleses(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service QuotesService instance
	 */
	protected void setQuotesService(QuotesService service) {
		this.quotesService = service;
	}

}
