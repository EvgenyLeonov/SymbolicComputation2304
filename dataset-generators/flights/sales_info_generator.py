import random

from flights.Person import Person
from flights.RouteInfo import RouteInfo

TEAM_ID = 7
FAMILIES_TOTAL_COUNT = 150  # random.randint(50, 80)
TOURS_TOTAL_COUNT = 150  # random.randint(50, 80)

routes_all = [
	RouteInfo("Krakow", "Zagreb", [700, 800, 900]),
	RouteInfo("Hamburg", "Innsbruck", [300, 900, 1200]),
	RouteInfo("Brno", "Rijeka", [600, 700, 1100, 1200]),
	RouteInfo("Hamburg", "Rome", [700, 800]),
	RouteInfo("Vienna", "Warsaw", [700, 900, 1100]),
	RouteInfo("Berlin", "Napoli", [700, 800, 900, 1000, 1100]),
	RouteInfo("Innsbruck", "Warsaw", [500, 900, 1100, 1200]),
	RouteInfo("Prague", "Rijeka", [600, 1000])
]

routes_tours = []
for r in routes_all:
	if r.suitable_for_organized_tours():
		routes_tours.append(r)

with open("names.txt") as file:
	all_names = [line.rstrip() for line in file]

with open("surnames.txt") as file:
	all_surnames = [line.rstrip() for line in file]

all_names_r = len(all_names)
all_surnames_r = len(all_surnames)


def generate_family():
	sur_ind = random.randint(0, all_surnames_r - 1)
	surname = all_surnames[sur_ind]
	num_of_children = random.randint(1, 2)
	total_num = num_of_children + 2
	family = []
	full_names_cache = []
	for n in range(total_num):
		n_ind = random.randint(0, all_names_r - 1)
		name = all_names[n_ind]
		age = random.randint(30, 50)
		if n > 1:
			age = random.randint(5, 10)
		family.append(Person(surname, name, age))
	return family


def generate_organized_tour():
	persons_num = random.randint(3, 6)
	tour = []
	for n in range(persons_num):
		sur_ind = random.randint(0, all_surnames_r - 1)
		surname = all_surnames[sur_ind]
		n_ind = random.randint(0, all_names_r - 1)
		name = all_names[n_ind]
		age = random.randint(30, 50)
		tour.append(Person(surname, name, age))
	return tour


families_count = 0
tours_count = 0
persons_routes_direct = []

while (families_count < FAMILIES_TOTAL_COUNT) or (tours_count < TOURS_TOTAL_COUNT):
	is_family = False
	is_tour = False
	if families_count >= FAMILIES_TOTAL_COUNT:
		is_tour = True
	else:
		if tours_count >= TOURS_TOTAL_COUNT:
			is_family = True
		else:
			i = random.randint(0, 1)
			if i == 0:
				is_family = True
			else:
				is_tour = True
	# generate people
	if is_family:
		persons = generate_family()
		routes_to_use = routes_all
		families_count = families_count + 1
	else:
		persons = generate_organized_tour()
		routes_to_use = routes_tours
		tours_count = tours_count + 1
	# define route
	route_to_flight = routes_to_use[random.randint(0, len(routes_to_use) - 1)]
	prices_options = []
	if is_family:
		for prc in route_to_flight.prices:
			if prc < 1000:
				prices_options.append(prc)
	else:
		for prc in route_to_flight.prices:
			if prc >= 1000:
				prices_options.append(prc)
	price_random = random.randint(1, 100)
	price_index = -1
	if len(prices_options) == 2:
		if price_random >= 31:
			price_index = 1
		else:
			price_index = 0
	else:
		if len(prices_options) == 3:
			if 0 <= price_random <= 25:
				price_index = 0
			else:
				if 26 <= price_random <= 75:
					price_index = 1
				else:
					price_index = 2
		else:
			price_index = 0

	if price_index == -1:
		print("ERROR: undefined price_index")

	original_price = prices_options[price_index]
	perc = random.randint(1, 4)
	special_price = original_price + int(perc / 100 * original_price)
	for p in persons:
		p.city1 = route_to_flight.city1
		p.city2 = route_to_flight.city2
		p.price_for_flight = original_price
		p.price_for_flight_modified = special_price
		persons_routes_direct.append(p)

with open(f"sales_team_{TEAM_ID}.csv", "w") as f:
	f.write("NAME,YOB,DEPARTURE,DESTINATION,PAID\n")
	for p in persons_routes_direct:
		f.write(f"{p.get_full_name()},{p.yob},{p.city1},{p.city2},{p.price_for_flight_modified}\n")

with open(f"broker_team_{TEAM_ID}.csv", "w") as f:
	f.write("NAME,YOB,DEPARTURE,DESTINATION,PAID\n")
	for p in persons_routes_direct:
		f.write(f"{p.get_full_name()},{p.yob},{p.city2},{p.city1},{p.price_for_flight}\n")

print(f"FAMILIES_TOTAL_COUNT={FAMILIES_TOTAL_COUNT}, TOURS_TOTAL_COUNT={TOURS_TOTAL_COUNT}")
print(f"open dataset persons count = {len(persons_routes_direct)}")


