package fr.pa1007.motor;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.gpio.extension.pca.PCA9685Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import fr.pa1007.trobotframework.move.Motor;
import fr.pa1007.trobotframework.utils.Utils;
import java.io.IOException;

public class MotorsImpl {

    public static final int                 MAX_VALUE = 21999;
    public static       Motor               MOTOR_1   = new MotorImpl(PCA9685Pin.PWM_04, RaspiPin.GPIO_00);
    public static       Motor               MOTOR_2   = new MotorImpl(PCA9685Pin.PWM_05, RaspiPin.GPIO_02);
    private static      PCA9685GpioProvider provider;

    public  static PCA9685GpioProvider getProvider() {
        if (provider == null) {
            try {
                provider = new PCA9685GpioProvider(
                        I2CFactory.getInstance(I2CBus.BUS_1),
                        0x40,
                        PCA9685GpioProvider.ANALOG_SERVO_FREQUENCY
                );
            }
            catch (IOException | I2CFactory.UnsupportedBusNumberException e) {
                Utils.getLogger().error(e);
            }
        }
        return provider;
    }
}
