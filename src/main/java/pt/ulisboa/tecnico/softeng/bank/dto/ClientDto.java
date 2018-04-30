package pt.ulisboa.tecnico.softeng.bank.dto;

public class ClientDto {
    String code;
    String id;
    String name;
    int age;

    public String getCode() {
				return this.code;
    }
    public void setCode(String code) {
				this.code = code;
		}

		public String getId() {
				return this.id;
    }
    public void setId(String id) {
				this.id = id;
		}

    public String getName() {
				return this.name;
		}
		public void setName(String name) {
				this.name = name;
		}

		public int getAge() {
				return this.age;
		}

		public void setAge(int age) {
				this.age = age;
		}
}
