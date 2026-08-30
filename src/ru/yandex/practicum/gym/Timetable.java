package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        ArrayList<TrainingSession> listOfTraining;
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> treeMap;

        if (timetable.containsKey(dayOfWeek)) {
            treeMap = timetable.get(dayOfWeek);
            if (treeMap.containsKey(timeOfDay)) {
                listOfTraining = treeMap.get(timeOfDay);
                listOfTraining.add(trainingSession);
            } else {
                listOfTraining = new ArrayList<>();
                listOfTraining.add(trainingSession);
                treeMap.put(timeOfDay,listOfTraining);
            }
        } else {
            listOfTraining = new ArrayList<>();
            listOfTraining.add(trainingSession);
            treeMap = new TreeMap<>();
            treeMap.put(timeOfDay, listOfTraining);
            timetable.put(dayOfWeek, treeMap);
        }
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        ArrayList<TrainingSession> listAllTrainingForDay = new ArrayList<>();

        if (timetable.containsKey(dayOfWeek)) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> currentTreeMap = timetable.get(dayOfWeek);
            for (ArrayList<TrainingSession> list : currentTreeMap.values()) {
                listAllTrainingForDay.addAll(list);
            }
        }
        return listAllTrainingForDay;
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        ArrayList<TrainingSession> listAllTrainingForDayAndTime = new ArrayList<>();
        if (timetable.containsKey(dayOfWeek)) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> currentTreeMap = timetable.get(dayOfWeek);
            if (currentTreeMap.containsKey(timeOfDay)) {
                listAllTrainingForDayAndTime = currentTreeMap.get(timeOfDay);
                return new ArrayList<>(listAllTrainingForDayAndTime);
            }
        }
        return listAllTrainingForDayAndTime;
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> mapCountOfCoachSession = new HashMap<>();

        for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> treeMap: timetable.values()) {
            for (ArrayList<TrainingSession> list : treeMap.values()) {
                for (TrainingSession session : list) {
                        mapCountOfCoachSession.put(session.getCoach(), mapCountOfCoachSession.getOrDefault(session.getCoach(), 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> listOfCoach = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : mapCountOfCoachSession.entrySet()) {
                listOfCoach.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        Collections.sort(listOfCoach);
        return listOfCoach;
    }
}
