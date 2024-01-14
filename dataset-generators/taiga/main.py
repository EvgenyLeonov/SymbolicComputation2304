import random

import numpy as np

MAP_WIDTH = 100
MAP_HEIGHT = 80

main_summits = [
    [20, 60, 30]
    , [20, 25, 25]
    , [70, 60, 15]
    , [70, 25, 15]
    , [90, 40, 25]
    , [90, 10, 10]
    , [90, 70, 10]
]

main_map = np.zeros((MAP_WIDTH, MAP_HEIGHT))


def generate_landscape():
    for summit in main_summits:
        add_mountain(summit)
    for x in range(0, MAP_WIDTH):
        for y in range(0, MAP_HEIGHT):
            current_value = main_map[x][y]
            if current_value == 0:
                main_map[x][y] = random.randint(1, 2)
    generate_js()
    generate_clojure()


def calc_height(summit, step):
    x_summit = summit[0]
    y_summit = summit[1]
    z_summit = summit[2]
    x_left = x_summit - step
    x_right = x_summit + step
    y_top = y_summit - step
    y_bottom = y_summit + step
    changed_points = []
    for x in range(x_left, x_right + 1):
        for y in range(y_top, y_bottom + 1):
            if (0 <= x < MAP_WIDTH) and (0 <= y < MAP_HEIGHT):
                current_value = main_map[x][y]
                z = z_summit - step
                if current_value < z:
                    changed_points.append([x, y, z])
    return changed_points


def add_mountain(summit):
    step = 1

    z_summit = summit[2]
    main_map[summit[0]][summit[1]] = z_summit

    while z_summit - step > 0:
        points_to_change = calc_height(summit, step)
        if len(points_to_change) > 0:
            for p in points_to_change:
                x = p[0]
                y = p[1]
                main_map[x][y] = p[2]
        step = step + 1


def generate_js():
    output = "var data = new vis.DataSet();\n"
    for x in range(0, MAP_WIDTH):
        for y in range(0, MAP_HEIGHT):
            val = int(main_map[x][y])
            output = output + f"data.add([{{x:{x},y:{y},z:{val},t:0,style:{val}}}]);\n"
    with open("data.js", "w") as f:
        f.write(output)


def generate_clojure():
    output = "(ns clojure-course.week14.rescue_mission_data)\n"
    output = output + "(def data [\n"
    for x in range(0, MAP_WIDTH):
        for y in range(0, MAP_HEIGHT):
            val = int(main_map[x][y])
            output = output + f"[{x} {y} {val}]\n"
    output = output + "])\n"
    with open("rescue_mission_data.clj", "w") as f:
        f.write(output)


if __name__ == '__main__':
    generate_landscape()
