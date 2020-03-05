package fr.pa1007.motor;

import com.pi4j.io.i2c.I2CFactory;
import fr.pa1007.trobotframework.move.Servo;
import fr.pa1007.trobotframework.utils.Module;
import java.io.IOException;

public class ServoImpl extends Servo {

    public ServoImpl(Module main) throws IOException, I2CFactory.UnsupportedBusNumberException {
        super(main);
    }
}
