class Person:
	def __init__(self, surname, name, age):
		self.surname = surname
		self.name = name
		self.yob = 2023 - age
		self.city1 = ""
		self.city2 = ""
		self.price_for_flight = 0

	def get_full_name(self):
		return self.name + " " + self.surname
