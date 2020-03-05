package fr.pa1007;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.io.i2c.I2CFactory;
import fr.pa1007.motor.MotorsImpl;
import fr.pa1007.motor.ServoImpl;
import fr.pa1007.trobotframework.event.ApplicationSelectedEvent;
import fr.pa1007.trobotframework.event.ModuleLoadedEvent;
import fr.pa1007.trobotframework.move.LinkedMotors;
import fr.pa1007.trobotframework.move.Motor;
import fr.pa1007.trobotframework.move.Servo;
import fr.pa1007.trobotframework.server.ServerUDP;
import fr.pa1007.trobotframework.utils.Module;
import java.io.IOException;

public class Audio extends Module {

    private ServerUDP    serv;
    private Servo        servo;
    private Motor        m1;
    private Motor        m2;
    private LinkedMotors lM;

    @Override
    public void appSelected(ApplicationSelectedEvent applicationSelectedEvent) {
        try {
            while (true) {
                String s = serv.waitServer();
                logger.debug(s);
                switch (s.trim().toUpperCase()) {
                    case "+10":
                        lM.addSpeed(10);
                        break;
                    case "+100":
                        lM.addSpeed(100);
                        break;
                    case "+1000":
                        lM.addSpeed(1000);
                        break;
                    case "-10":
                        lM.addSpeed(-10);
                        break;
                    case "-100":
                        lM.addSpeed(-100);
                        break;
                    case "-1000":
                        lM.addSpeed(-1000);
                        break;
                    case "MAX":
                        lM.changeSpeed(MotorsImpl.MAX_VALUE);
                        break;
                    case "STOP":
                        lM.stop();
                        break;
                    case "HAUT":
                        lM.forward();
                        break;
                    case "BAS":
                        lM.reverse();
                        break;
                    case "GAUCHE":
                        servo.move(servo.getAngle() - 1);
                        break;
                    case "DROITE":
                        servo.move(servo.getAngle() + 1);
                        break;
                }
            }
        }
        catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public Class<ModuleLoadedEvent> getEventClass() {
        return null;
    }

    @Override
    public void listener(ModuleLoadedEvent moduleLoadedEvent) {
        try {
            serv = ServerUDP.getInstance();
            m1 = MotorsImpl.MOTOR_1;
            m2 = MotorsImpl.MOTOR_2;
            lM = new LinkedMotors(m1, m2);
            servo = new ServoImpl(this);
            servo.getPwm().setPWMFreqency(PCA9685GpioProvider.ANALOG_SERVO_FREQUENCY.floatValue());
        }
        catch (IOException | I2CFactory.UnsupportedBusNumberException e) {
            logger.error(e);
        }
    }
}
