package app;

import app.mkt.client.MktDataClient;
import app.mkt.client.MktEmailClient;
import app.mkt.client.MktSMSClient;
import app.mkt.dtos.CustomerDTO;
import app.mkt.dtos.OrderDTO;
import app.mkt.dtos.ProductDTO;
import app.mkt.dtos.emails.EmailContactDTO;
import app.mkt.dtos.emails.WaitingListSubscriptionDTO;
import app.mkt.dtos.sms.MessageDTO;
import app.mkt.dtos.sms.SMSContactDTO;

import java.util.List;

/**
 * Main service for marketing operations.
 * Provides methods for importing products, orders, customers, and managing email/SMS subscriptions.
 * Implements singleton pattern for instance management.
 */
public class MarketingService
{

    protected static volatile MarketingService instance = null;

    protected static final Object lock = new Object();

    /**
     * Gets the singleton instance of MarketingService.
     * Thread-safe double-checked locking implementation.
     *
     * @return MarketingService instance
     */
    public static MarketingService instance()
    {
        if (instance != null)
        {
            return instance;
        }

        synchronized (lock)
        {
            if (instance == null)
            {
                instance = new MarketingService();
            }
        }

        return instance;
    }

    /**
     * Imports a product to the marketing service.
     *
     * @param productDTO product DTO
     * @return true if successful
     * @throws Exception if import fails
     */
    public boolean importProduct(ProductDTO productDTO) throws Exception
    {
        MktDataClient client = new MktDataClient();

        boolean imported = client.postProduct(productDTO);

        if (!imported)
        {
            throw new Exception("[MarketingService] :: [importProduct] :: Importation failed for " + productDTO.getSku());
        }

        return imported;
    }

    /**
     * Imports a collection of products to the marketing service.
     *
     * @param productDTOCollection list of product DTOs
     * @return true if successful
     * @throws Exception if import fails
     */
    public boolean importProductCollection(List<ProductDTO> productDTOCollection) throws Exception
    {
        MktDataClient client = new MktDataClient();

        boolean imported = client.postProductCollection(productDTOCollection);

        if (!imported)
        {
            throw new Exception("[MarketingService] :: [importProductCollection] :: Importation failed");
        }

        return imported;
    }

    /**
     * Imports an order to the marketing service.
     *
     * @param orderDTO order DTO
     * @return true if successful
     * @throws Exception if import fails
     */
    public boolean importOrder(OrderDTO orderDTO) throws Exception
    {
        MktDataClient client = new MktDataClient();

        return client.postOrder(orderDTO);
    }

    /**
     * Imports a collection of orders to the marketing service.
     *
     * @param orderDTOCollection list of order DTOs
     * @return true if successful
     */
    public boolean importOrderCollection(List<OrderDTO> orderDTOCollection)
    {
        if (orderDTOCollection == null || orderDTOCollection.isEmpty())
        {
            return false;
        }

        try
        {
            MktDataClient client = new MktDataClient();
            return client.postOrderCollection(orderDTOCollection);
        }
        catch (Exception e)
        {
            System.err.println("[MarketingService] :: [importOrderCollection] - " + e.getMessage());
            return false;
        }
    }

    /**
     * Imports a customer to the marketing service.
     *
     * @param customerDTO customer DTO
     * @return true if successful
     * @throws Exception if import fails
     */
    public boolean importCustomer(CustomerDTO customerDTO) throws Exception
    {
        MktDataClient client = new MktDataClient();

        return client.postCustomer(customerDTO);
    }

    /**
     * Imports a collection of customers to the marketing service.
     *
     * @param customerDTOCollection list of customer DTOs
     * @return true if successful
     */
    public boolean importCustomerCollection(List<CustomerDTO> customerDTOCollection)
    {
        if (customerDTOCollection == null || customerDTOCollection.isEmpty())
        {
            return false;
        }

        try
        {
            MktDataClient client = new MktDataClient();
            return client.postCustomerCollection(customerDTOCollection);
        }
        catch (Exception e)
        {
            System.err.println("[MarketingService] :: [importCustomerCollection] - " + e.getMessage());
            return false;
        }
    }

    /**
     * Subscribes an email contact.
     *
     * @param contactDto email contact DTO
     * @return this instance for method chaining
     */
    public MarketingService subscribeEmail(EmailContactDTO contactDto)
    {
        MktEmailClient client = new MktEmailClient();

        try
        {
            boolean emailSubscribed = client.emailCreateContact(contactDto);
            System.out.println("[MarketingService] :: [subscribeEmail] - status " + emailSubscribed);
        }
        catch (Exception e)
        {
            System.err.println("[MarketingService] :: [subscribeEmail] - error :: " + e.getMessage());
        }

        return this;
    }

    /**
     * Subscribes an SMS contact.
     *
     * @param contactDto SMS contact DTO
     * @return this instance for method chaining
     */
    public MarketingService subscribeSMS(SMSContactDTO contactDto)
    {
        MktSMSClient client = new MktSMSClient();

        try
        {
            boolean smsSubscribed = client.smsCreateContact(contactDto);
            System.out.println("[MarketingService] :: [subscribeSMS] - status " + smsSubscribed);
        }
        catch (Exception e)
        {
            System.err.println("[MarketingService] :: [subscribeSMS] - error :: " + e.getMessage());
        }

        return this;
    }

    /**
     * Sends a transactional SMS message.
     *
     * @param messageId message ID
     * @param message message DTO
     */
    public void sendSMSTransactionalMessage(int messageId, MessageDTO message)
    {
        MktSMSClient client = new MktSMSClient();

        try
        {
            boolean smsSent = client.sendSMS(messageId, message);
            System.out.println(
                "[MarketingService] :: [sendSMSTransactionalMessage] - status: "
                + (smsSent ? "SUCCESS" : "FAILED")
                + " for: "
                + message.getObscuredPhoneNumber()
            );
        }
        catch (Exception e)
        {
            System.err.println("[MarketingService] :: [sendSMSTransactionalMessage] - " + e.getMessage());
        }
    }

    /**
     * Handles suppression list subscription.
     *
     * @param contactDTO email contact DTO
     * @return true if successful
     */
    public boolean handleSuppressionListSubscription(EmailContactDTO contactDTO)
    {
        MktEmailClient client = new MktEmailClient();

        boolean subscribed = false;

        try
        {
            String suppressionListIdStr = System.getenv("MKT_SUPPRESSION_LIST_ID");

            if (suppressionListIdStr == null || suppressionListIdStr.isEmpty())
            {
                throw new Exception("MKT_SUPPRESSION_LIST_ID env missing");
            }

            int suppressionListId = Integer.parseInt(suppressionListIdStr);
            subscribed = client.handleUserSubscriptionToList(contactDTO, suppressionListId);
        }
        catch (Exception e)
        {
            System.err.println("[MarketingService] :: [handleSuppressionListSubscription] " + e.getMessage());
        }
        finally
        {
            System.out.println(
                "[MarketingService] :: [handleSuppressionListSubscription] - status: "
                + (subscribed ? "SUCCESS" : "FAILED")
                + " for: "
                + contactDTO.getObscuredEmail()
            );
        }

        return subscribed;
    }

    /**
     * Updates a waitlist subscription.
     *
     * @param waitingListSubscriptionDTO waiting list subscription DTO
     * @return true if successful
     */
    public boolean updateWaitList(WaitingListSubscriptionDTO waitingListSubscriptionDTO)
    {
        MktEmailClient client = new MktEmailClient();

        boolean waitListUpdated = false;

        try
        {
            String backInStockListIdStr = System.getenv("MKT_ALERTS_BACK_IN_STOCK_LIST_ID");

            if (backInStockListIdStr == null || backInStockListIdStr.isEmpty())
            {
                throw new Exception("MKT_ALERTS_BACK_IN_STOCK_LIST_ID env missing");
            }

            int backInStockListId = Integer.parseInt(backInStockListIdStr);
            waitListUpdated = client.updateWaitlist(waitingListSubscriptionDTO, backInStockListId);

            System.out.println("[MarketingService] :: [updateWaitList] - status " + waitListUpdated);
        }
        catch (Exception e)
        {
            System.err.println("[MarketingService] :: [updateWaitList] - " + e.getMessage());
        }

        return waitListUpdated;
    }
}

