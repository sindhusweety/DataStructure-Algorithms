'''

This MaximizationOptimalSolution built to find Optimal Points & Optimal Solution
It developed to generate the result upon lists of triples of numbers ai1 , ai2 , and bi (x, y )
Maximize: 20x1 + 60x2
Subject to: 3x1 + 4x2 ≤ 400
4x1 + 2x2 ≤ 320
6x1 + x2 ≤ 120
x1 ≥ 0, x2 ≥ 0
'''

'''class Arguments:
    def __init__(self):
        self.op = 'Maximization....'
        self.equations = ['3x1 + 4x2 ≤ 400', '4x1 + 2x2 ≤ 320', '6x1 + x2 ≤ 120']
        self.obj_atrributes = [20, 60]
        self.decision_variables = [[[3, 4],[4, 2],[6,1]],[400, 320, 120]]
        self.lowbound = "x1, x2 >= 0"'''


class Arguments:
    def __init__(self):
        self.op = 'Maximization....'
        self.equations = ['3x1 + 4x2 ≤ 400', '4x1 + 2x2 ≤ 320', '6x1 + x2 ≤ 120']
        self.obj_atrributes = [2, 3]
        self.decision_variables = [[[1, 2],[1, 3],[1,4]],[2, 3, 4]]
        self.lowbound = "x1, x2 >= 0"


class MaximizationOptimalSolution:
    def __init__(self):
        self.obj = Arguments()
        print(self.obj.op)
        points = self.find_optimal_points()
        self.find_optimal_solution(points)

    def find_optimal_solution(self, points):

        zvalue_points = dict()
        MAX = 0
        for p in points:
            z_value = (self.obj.obj_atrributes[0] * p[0]) + (self.obj.obj_atrributes[1] * p[1])
            MAX = max(z_value, MAX)
            zvalue_points[z_value] = p

        print("Optimal Points ",  zvalue_points[MAX])
        print('Optimal number of x to produce is: {x}'.format( x=zvalue_points[MAX][0]))
        print('Optimal number of y to produce is: {y}'.format(y=zvalue_points[MAX][1]))
        print("Optimal Solution ", MAX)


    def find_optimal_points(self):
        lpoints = list()
        lxais = list()
        lyaxis = list()
        for eindx in range(len(self.obj.decision_variables[1])):
            equation = self.obj.decision_variables[0][eindx], self.obj.decision_variables[1][eindx]
            corner_points = [[round(equation[1]/ equation[0][0]), 0 ], [0, round(equation[1]/ equation[0][1])]]
            print( "Equation {arg_equation} 's corner pointers : ".format(arg_equation = self.obj.equations[eindx]) , corner_points)
            lxais.append(corner_points[0])
            lyaxis.append(corner_points[1])
        for dindx in range(len(self.obj.decision_variables[0])-1):
            equations = [self.obj.decision_variables[0][dindx : dindx + 2], self.obj.decision_variables[1][dindx :  dindx+2] ]
            x, y= self.apply_arthm_operations(equations, args_equations_index = [ dindx, dindx+1 ])
            if x >= 0 and y >= 0: #condition x & y must be above zero
                print(x, y)
                lpoints.append([x, y])
        lpoints.append((sorted(lxais))[0])
        lpoints.append((sorted(lyaxis))[0])

        return lpoints

    def apply_arthm_operations(self, equations, args_equations_index : list):
        eq1 = [ v * equations[0][1][1] for v in equations[0][0]], equations[1][0] * equations[0][1][1]
        eq2 = [ v * equations[0][0][1] for v in  equations[0][1]], equations[1][1] * equations[0][0][1]
        #print(eq1)
        #print(eq2)
        x = (eq1[1] - eq2[1]) / (eq1[0][0] - eq2[0][0])
        #print(x, eq1[1],  eq2[1], eq1[0][0] , eq2[0][0], eq1[1]- eq2[1], eq1[0][0] - eq2[0][0])
        y = (equations[1][0] - (equations[0][0][0] * x)) / equations[0][0][1]
        #print(y, 'y', equations[1][0] , equations[0][0][0] , x, equations[0][0][1])
        print ('Equations ', self.obj.equations[args_equations_index[0]] ,'and ', self.obj.equations[args_equations_index[1]], ' : '  , x , y)

        return x, y



MaximizationOptimalSolution()

