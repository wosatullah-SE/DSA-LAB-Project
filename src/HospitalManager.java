import java.util.ArrayList;

public class HospitalManager {

    private ArrayList<Hospital> hospitals;

    public HospitalManager() {

        hospitals = new ArrayList<>();
    }

    public void addHospital(Hospital hospital) {

        hospitals.add(hospital);
    }

    public ArrayList<Hospital> getHospitals() {

        return hospitals;
    }

    public Hospital searchHospital(int id) {

        for(Hospital hospital : hospitals) {

            if(hospital.getHospitalId() == id) {

                return hospital;
            }
        }

        return null;
    }

    public void deleteHospital(int id) {

        hospitals.removeIf(
                hospital ->
                        hospital.getHospitalId() == id
        );
    }
}