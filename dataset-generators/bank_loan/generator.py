import random

from bank_loan.Person import Person

NUMBER_OF_PERSONS = 120
MIN_SALARY = 10
MAX_SALARY = 100
YEAR_START = 2010
YEAR_END = 2021

with open("names.txt") as file:
    all_names = [line.rstrip() for line in file]

with open("surnames.txt") as file:
    all_surnames = [line.rstrip() for line in file]

all_names_r = len(all_names)
all_surnames_r = len(all_surnames)
full_names_cache = []
all_persons = []

# persons
while len(all_persons) < NUMBER_OF_PERSONS:
    n_ind = random.randint(0, all_names_r - 1)
    sur_ind = random.randint(0, all_surnames_r - 1)
    name = all_names[n_ind]
    surname = all_surnames[sur_ind]
    full_name = name + " " + surname
    if full_name not in full_names_cache:
        prs = Person()
        prs.full_name = full_name
        all_persons.append(prs)
        full_names_cache.append(full_name)

# month income
delta = 30
strata_count = int(NUMBER_OF_PERSONS / 3)
for strata in range(1, 4):
    min_salary = (strata - 1) * delta + MIN_SALARY
    max_salary = strata * delta + MIN_SALARY
    print(f"min_salary={min_salary}; max_salary={max_salary}")
    border1 = (strata - 1) * strata_count
    border2 = strata * strata_count - 1
    print(f"border1={border1}; border2={border2}")
    for index_person in range(border1, border2 + 1):
        pers = all_persons[index_person]
        pers.strata = strata
        pers.month_salary = random.randint(min_salary, max_salary) * 1000

with open("customer_personal_data.txt", "w") as fp:
    fp.write("Name,Salary\n")
    for person in all_persons:
        fp.write(f"{person.full_name},{person.month_salary}\n")

print("customer_personal_data.txt >>> created")

# risk
for person in all_persons:
    person.risk_category = random.randint(0, 2)

# loans for each customer
risks = [30, 45, 55]
for person in all_persons:
    current_month = random.randint(1, 12)
    current_year = YEAR_START
    while current_year < YEAR_END:
        loan_name = "loan_" + str(len(person.loans) + 1)
        day_start = random.randint(1, 25)
        month_start = current_month
        year_start = current_year
        duration = random.randint(4, 12)
        share = risks[person.risk_category] + random.randint(-10, 10)
        amount = int(person.month_salary * share / 100 * duration)
        month_end = month_start + duration
        if month_end > 12:
            month_end = month_end - 12
            current_year = current_year + 1
        loan_info = f"{loan_name},{day_start:02}-{month_start:02}-{year_start},{amount},{day_start:02}-{month_end:02}-{current_year},{duration}"
        person.loans.append(loan_info)

with open("loans.txt", "w") as fp:
    fp.write("Customer,Loan ID,Date Start,Amount,Date Finish,Duration\n")
    for person in all_persons:
        for loan in person.loans:
            fp.write(f"{person.full_name},{loan}\n")

print("loans.txt >>> created")

# payments
risk_delta = 20
for person in all_persons:
    for loan in person.loans:
        tokens = loan.split(",")
        loan_name = tokens[0]
        date_start = tokens[1]
        total_amount = int(tokens[2])
        duration = int(tokens[4])
        payment_amount = int(total_amount / duration)
        tokens_date = date_start.split("-")
        current_year = int(tokens_date[2])
        current_day = int(tokens_date[0])
        # start to pay interest from the next month
        current_month = int(tokens_date[1]) + 1
        if current_month > 12:
            current_month = 1
            current_year = current_year + 1

        paid_amount = 0
        while paid_amount < total_amount:
            risk = risks[person.risk_category] - risk_delta
            if person.strata == 1:
                risk = risk + 5
            skip = 0 <= random.randint(1, 100) <= risk
            if not skip:
                paid_amount = paid_amount + payment_amount
                payment_info = f"{loan_name},{current_day:02}-{current_month:02}-{current_year},{payment_amount}"
                person.payments.append(payment_info)
            current_month = current_month + 1
            if current_month > 12:
                current_month = 1
                current_year = current_year + 1

with open("payments.txt", "w") as fp:
    fp.write("Customer,Loan ID,Date Start,Paid\n")
    for person in all_persons:
        for payments in person.payments:
            fp.write(f"{person.full_name},{payments}\n")

print("payments.txt >>> created")



