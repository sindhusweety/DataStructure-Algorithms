import pulp as p

# Set Up a LP Maximization Problem:
Lp_prob = p.LpProblem('Manufacturer-Analysis', p.LpMaximize)  # Here we named the Problem "Manufacturer-Analysis".

# Set Up Problem Variables:
x = p.LpVariable("x", lowBound=0)  # "x" for model I
y = p.LpVariable("y", lowBound=0)  # "y" for model II
#z = p.LpVariable("z", lowBound=150)  # "z" for model III

# Create Objective Function:
#Lp_prob += 30 * x + 20 * y + 50 * z
Lp_prob += 2 * x + 3 * y

# Create Constraints:
Lp_prob += 1 * x + 2 * y   <= 2
Lp_prob += 1 * x + 3 * y   <= 3
Lp_prob += 1 * x + 4 * y   <= 4
Lp_prob += 1 * x + 5 * y   <= 5
Lp_prob += 1 * x + 6 * y   <= 6
#Lp_prob += 6 * x + 3 * y + 2 * z  <= 9000



# Show the problem:
print(Lp_prob)  # note that it's shown in alphabetical order

# Solve the Problem:
status = Lp_prob.solve()
print(p.LpStatus[status])  # Display Solution Status

# Printing the final solution
print(p.value(x), p.value(y), p.value(Lp_prob.objective))

# Printing Number of Model I, II & III:
for var in (x, y):
    print('Optimal number of {} to produce is: {:1.0f}'
          .format(var.name, var.value()))
