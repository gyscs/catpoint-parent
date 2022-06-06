//package com.udacity.catpoint;
//
//import com.udacity.catpoint.security.service.SecurityService;
//import com.udacity.catpoint.security.data.*;
//import com.udacity.catpoint.service.ImageService;
//import com.udacity.catpoint.security.data.SecurityRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.EnumSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//
//import java.awt.image.BufferedImage;
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.mockito.ArgumentMatchers.any;
//
///**
// * Unit test for simple App.
// */
//
//@ExtendWith(MockitoExtension.class)
//public class AppTest
//{
//
//    private SecurityService securityService;
//
//    private Sensor sensor;
//    private Set<Sensor> sensors;
//
//    @Mock
//    private SecurityRepository securityRepository;
//
//    @Mock
//    private ImageService imageService;
//
//
//    @BeforeEach
//    void init() {
//        securityService = new SecurityService(securityRepository, imageService);
//        sensor = new Sensor("a", SensorType.DOOR);
//        Sensor sensorB = new Sensor("b", SensorType.DOOR);
//        Sensor sensorC = new Sensor("c", SensorType.DOOR);
//        sensors = new HashSet<>();
//        sensors.add(sensor);
//        sensors.add(sensorB);
//        sensors.add(sensorC);
//    }
//
//    // case 1
//    @Test
//    public void alarmArmed_sensorActivated_systemPendingAlarm() {
//        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
//        Mockito.when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);
//        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.NO_ALARM);
//        securityService.changeSensorActivationStatus(sensor, true);
//        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.PENDING_ALARM);
//    }
//
//    // case 2
//    @Test
//    public void alarmArmed_sensorActivated_pendingAlarm_setAlarm() {
//        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
//        Mockito.when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);
//        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
//        securityService.changeSensorActivationStatus(sensor, true);
//        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
//    }
//
//    // case 3
//    @Test
//    public void pendingAlarm_allSensorInactive_returnToNoAlarm() {
//        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
//        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
//        securityService.changeSensorActivationStatus(sensor, false);
//        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
//    }
//
//    // case 4
//    @ParameterizedTest
//    @ValueSource(booleans = {true, false})
//    public void alarmActive_changeSensorState_notAffectAlarmState(boolean state) {
//        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
//        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.ALARM);
//        securityService.changeSensorActivationStatus(sensor, state);
//        Mockito.verify(securityRepository, Mockito.never()).setAlarmStatus(any(AlarmStatus.class));
//    }
//
//    // case 5
//    @Test
//    public void alreadyActive_activateSensor_systemPendingState_changeToAlarmState() {
//        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
//        sensor.setActive(true);
//        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
//        securityService.changeSensorActivationStatus(sensor, true);
//        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
//    }
//
//    // case 6
//    @Test
//    public void alreadyInactive_deactiveSensor_noChangeToAlarmState() {
//        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
//        securityService.changeSensorActivationStatus(sensor, false);
//        Mockito.verify(securityRepository, Mockito.never()).setAlarmStatus(any(AlarmStatus.class));
//    }
//
//    // case 7
//    @Test
//    public void catImage_armedHome_systemAlarmState() {
//        BufferedImage catImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
//        Mockito.when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);
//        Mockito.when(imageService.imageContainsCat(catImage, 50.0f)).thenReturn(true);
//        securityService.processImage(catImage);
//        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
//    }
//
//    // case 8
//    @Test
//    public void notCatImage_changeToNoAlarm_asLongAsSensorsNotActive() {
//        BufferedImage notCatImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
//        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
//        securityService.processImage(notCatImage);
//        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
//    }
//
//    // case 9
//    @Test
//    public void disarmedSystem_setToNoAlarm() {
//        securityService.setArmingStatus(ArmingStatus.DISARMED);
//        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
//    }
//
//    // case 10
//    @ParameterizedTest
//    @EnumSource(value = ArmingStatus.class, names = {"ARMED_HOME", "ARMED_AWAY"})
//    public void armedSystem_resetSensorsToInactive(ArmingStatus state) {
//        sensors.forEach(sensor -> sensor.setActive(true));
//        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
//        securityService.setArmingStatus(state);
//        securityRepository.getSensors().forEach(sensor -> assertFalse(sensor.getActive()));
//    }
//
//    // case 11
//    @Test
//    public void armedHome_showsCat_setToAlarm() {
//        Mockito.when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);
//        BufferedImage catImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
//        Mockito.when(imageService.imageContainsCat(catImage, 50.0f)).thenReturn(true);
//        securityService.processImage(catImage);
//        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
//    }
//}
