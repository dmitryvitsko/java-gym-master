package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        ArrayList<TrainingSession> testListForMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, testListForMonday.size());
        ArrayList<TrainingSession> testListForTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, testListForTuesday.size());
        Assertions.assertEquals(singleTrainingSession, testListForMonday.get(0));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        ArrayList<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(mondayChildTrainingSession, mondaySessions.get(0));
        ArrayList<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySessions.size());
        Assertions.assertEquals(thursdayChildTrainingSession, thursdaySessions.get(0));
        Assertions.assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(1));
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        ArrayList<TrainingSession> mondaySessions1 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, singleTrainingSession.getTimeOfDay());
        Assertions.assertEquals(1, mondaySessions1.size());
        ArrayList<TrainingSession> mondaySessions2 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertEquals(0, mondaySessions2.size());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession2);

        Group group2 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach2 = new Coach("Александрович", "Павел", "Владимирович");
        TrainingSession singleTrainingSession3 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(singleTrainingSession3);

        List<CounterOfTrainings> list = timetable.getCountByCoaches();

        Assertions.assertEquals(coach, list.get(0).getCoach());
        Assertions.assertEquals(coach2, list.get(1).getCoach());
        Assertions.assertEquals(2, list.get(0).getCounter());
        Assertions.assertEquals(1, list.get(1).getCounter());
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void testGetCountByCoachesIsEmpty() {
        Timetable timetable = new Timetable();
        List<CounterOfTrainings> list = timetable.getCountByCoaches();

        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    void testGetcountByCoachesAnotherDays() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 30);
        Group group3 = new Group("Гимнастика для взрослых", Age.ADULT, 40);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSession1 = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group2, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));
        TrainingSession singleTrainingSession3 = new TrainingSession(group3, coach1,
                DayOfWeek.FRIDAY, new TimeOfDay(19, 30));

        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);

        List<CounterOfTrainings> list = timetable.getCountByCoaches();
        Assertions.assertEquals(3, list.get(0).getCounter());
    }

}
