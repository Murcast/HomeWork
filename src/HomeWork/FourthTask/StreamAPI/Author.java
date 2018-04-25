package HomeWork.FourthTask.StreamAPI;

import java.time.LocalDate;

class Author {
    String name;
    LocalDate dateOfBirth;
    LocalDate dateOfDeath;
    boolean gender; //true if male

    public Author(String name, LocalDate dateOfBirth, LocalDate dateOfDeath, boolean gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public boolean isMale() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    @Override
    public String toString(){
        return name + ", " + (getFullYears(getDateOfDeath() == null ? LocalDate.now() : getDateOfDeath(), getDateOfBirth()));
    }

    /**
     * Method calculates the count of full years between the specified dates
     *
     * @param date1 the date from which we subtract
     * @param date2 the date that we subtract
     * @return the number of full years between the specified dates
     */
    public static int getFullYears(LocalDate date1, LocalDate date2){
        int year1 = date1.getYear();
        int year2 = date2.getYear();
        int month1 = date1.getMonth().getValue();
        int month2 = date2.getMonth().getValue();
        int day1 = date1.getDayOfMonth();
        int day2 = date2.getDayOfMonth();

        int age = year1 - year2;

        if (year1 > year2) {
            if (month1 < month2) {
                return age - 1;
            } else if (month1 == month2) {
                if (day1 < day2) {
                    return age - 1;
                } else {
                    return age;
                }
            } else {
                return age;
            }
        } else if (year1 == year2) {
            return 0;
        } else {
            if(month1 < month2) {
                return age;
            } else if (month1 == month2) {
                if (day1 > day2) {
                    return age + 1;
                } else {
                    return age;
                }
            } else {
                return age + 1;
            }
        }
    }
}
