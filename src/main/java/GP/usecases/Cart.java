package GP.usecases;

import GP.entities.Flower;
import GP.interceptors.LoggedInvocation;
import GP.utilities.OrderInfo;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class Cart implements Serializable
{
    @Getter @Setter
    private List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
}
