package by.shumilov;

import by.shumilov.model.*;
import by.shumilov.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(14)
                .limit(7)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equalsIgnoreCase("Japanese"))
                .peek(animal -> {
                    if (animal.getGender().equalsIgnoreCase("Female")) {
                        animal.setBread(animal.getBread().toUpperCase(Locale.ROOT));
                    }
                })
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .distinct()
                .filter(s -> s.startsWith("A"))
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        long femalesAnimals = animals.stream()
                .filter(animal -> animal.getGender().equalsIgnoreCase("Female"))
                .count();
        System.out.println(femalesAnimals);
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean anyMatchHungarian = animals.stream().filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> animal.getOrigin().equalsIgnoreCase("Hungarian"));
        System.out.println(anyMatchHungarian);
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isAllAnimalsMatchCondition = animals.stream().allMatch(animal ->
                animal.getGender().equalsIgnoreCase("Female") ||
                        animal.getGender().equalsIgnoreCase("male"));
        System.out.println(isAllAnimalsMatchCondition);
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isOriginOceania = animals.stream()
                .noneMatch(animal -> animal.getOrigin().equalsIgnoreCase("Oceania"));
        System.out.println(isOriginOceania);
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .mapToInt(Animal::getAge)
                .max()
                .ifPresent(System.out::println);
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().map(Animal::getBread)
                .map(String::toCharArray)
                .min(Comparator.comparingInt(o -> o.length))
                .ifPresent(chars -> System.out.println(chars.length));
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int sumOfAges = animals.stream().mapToInt(Animal::getAge).sum();
        System.out.println(sumOfAges);
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(animal -> animal.getOrigin().equalsIgnoreCase("Indonesian"))
                .mapToInt(Animal::getAge)
                .average()
                .ifPresent(System.out::println);
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        // Today is 11.02.2023. We should choose persons, who were born
        // between 12.02.1995 (who is still 27) and 11.02.2005 (who already 18)
        people.stream()
                .filter(person -> person.getGender().equalsIgnoreCase("male"))
                .filter(person ->
                        LocalDate.now().minusYears(18).plusDays(1).isAfter(person.getDateOfBirth()) &&
                                LocalDate.now().minusYears(28).isBefore(person.getDateOfBirth()))
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        Predicate<House> isHospitalPredicate = house -> house.getBuildingType().equalsIgnoreCase("Hospital");
        Predicate<Person> isOlderThen65 = person -> person.getDateOfBirth().isBefore(LocalDate.now().minusYears(65));
        Predicate<Person> isYoungerThen18 = person -> person.getDateOfBirth().isAfter(LocalDate.now().minusYears(18));
        Function<House, Stream<Person>> personFunction = house -> house.getPersonList().stream();

        Stream.concat(Stream.concat(
                                houses.stream()
                                        .filter(isHospitalPredicate)
                                        .flatMap(personFunction),
                                houses.stream()
                                        .filter(isHospitalPredicate.negate())
                                        .flatMap(personFunction)
                                        .filter(isOlderThen65.or(isYoungerThen18))),
                        houses.stream()
                                .filter(isHospitalPredicate.negate())
                                .flatMap(personFunction)
                                .filter(isOlderThen65.negate().and(isYoungerThen18.negate())))
                .limit(500)
                .forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        List<Car> rest = new ArrayList<>(List.copyOf(cars));

        List<Car> cars1 = Stream.concat(cars.stream().filter(car -> car.getCarMake().equalsIgnoreCase("Jaguar")),
                cars.stream()
                        .filter(car -> car.getColor().equalsIgnoreCase("White"))).toList();
        rest.removeAll(cars1);

        List<Car> cars2 = rest.stream()
                .filter(car -> car.getMass() < 1500)
                .filter(car ->
                        car.getCarMake().equalsIgnoreCase("BMW") ||
                                car.getCarMake().equalsIgnoreCase("Lexus") ||
                                car.getCarMake().equalsIgnoreCase("Chrysler") ||
                                car.getCarMake().equalsIgnoreCase("Toyota"))
                .toList();
        rest.removeAll(cars2);

        List<Car> cars3 = Stream.concat(
                        rest.stream()
                                .filter(car -> car.getColor().equalsIgnoreCase("Black"))
                                .filter(car -> car.getMass() > 4000),
                        rest.stream()
                                .filter(car ->
                                        car.getCarMake().equalsIgnoreCase("GMC") ||
                                                car.getCarMake().equalsIgnoreCase("Dodge")))
                .toList();
        rest.removeAll(cars3);

        List<Car> cars4 = rest.stream()
                .filter(car ->
                        car.getReleaseYear() < 1982 ||
                                car.getCarModel().equalsIgnoreCase("Civic") ||
                                car.getCarModel().equalsIgnoreCase("Cherokee"))
                .toList();
        rest.removeAll(cars4);

        List<Car> cars5 = rest.stream()
                .filter(car ->
                        !(car.getColor().equalsIgnoreCase("Yellow") ||
                                car.getColor().equalsIgnoreCase("Red") ||
                                car.getColor().equalsIgnoreCase("Green") ||
                                car.getColor().equalsIgnoreCase("Blue")) ||
                                car.getPrice() > 40_000
                )
                .toList();
        rest.removeAll(cars5);

        List<Car> cars6 = rest.stream()
                .filter(car -> car.getVin().contains("59"))
                .toList();
        rest.removeAll(cars6);

        List<Double> countryCostsList = Stream.of(cars1, cars2, cars3, cars4, cars5, cars6)
                .mapToDouble(value -> value.stream()
                        .mapToInt(Car::getMass)
                        .sum())
                .map(value -> value = value * 7.14 / 1000)
                .boxed()
                .toList();

        countryCostsList.forEach(x -> System.out.format("The cost of the echelon: %.2f \n", x));

        countryCostsList.stream()
                .reduce(Double::sum)
                .ifPresent(cost -> System.out.format("The total cost: %.2f \n", cost));
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        flowers.stream()
                .sorted(Comparator
                        .comparing(Flower::getOrigin).reversed()                                    //sorted by origin reversed
                        .thenComparing(Flower::getPrice)                                            //sorted by price
                        .thenComparing(Flower::getWaterConsumptionPerDay).reversed()                //sorted by water consumption
                        .thenComparing(Flower::getCommonName).reversed())                           //sorted by common name
                .filter(flower -> Pattern.matches("([C-S])(.+)", flower.getCommonName()))     //filter by first letter of name
                .filter(Flower::isShadePreferred)                                                   //filter by shade preferring
                .filter(flower -> flower.getFlowerVaseMaterial().contains("Glass") ||               //filter by vase material
                        flower.getFlowerVaseMaterial().contains("Aluminum") ||
                        flower.getFlowerVaseMaterial().contains("Steel"))
                .peek(System.out::println)
                .mapToDouble(flower ->                                                              //calculate total cost for every plant
                        flower.getPrice()
                                + flower.getWaterConsumptionPerDay() * 365 * 5 * 1.39 / 1000)
                .reduce(Double::sum)
                .ifPresent(cost -> System.out.format("The cost of greenhouse maintenance: %.2f \n", cost));
    }
}
