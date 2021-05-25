package GP.usecases;

import GP.interfaces.PaymentController;
import GP.utilities.OrderInfo;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
@Alternative
public class SimplePaymentController implements PaymentController {

    @Inject
    private Cart cart;

    public double getPrice()
    {
        double price = 0;
        for (OrderInfo o:
             cart.getOrderInfos()) {
            price += o.getFlowerPrice() * o.getFlowerAmount();
        }
        return price;
    }

    public boolean pay()
    {
        return true;
    }
}
