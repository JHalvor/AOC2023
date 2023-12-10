
limitCubes = {
    "red" : "12",
    "green" : "13",
    "blue" : "14"
}


def dayTwo(input):
    sum = 0
    minPowSum = 0

    for line in open(input, "r"):
        gameIsPossible = True
        maxR = 0
        maxG = 0
        maxB = 0

        lineData = line.split(":")
        id = int(lineData[0].split(" ")[1])
        game = lineData[1]

        game = game.replace(";", ",")
        
        cubes = game.split(",")
        for el in cubes:
            number = int(el.split()[0])
            color = el.split()[1]

            if color == "red":
                if number > maxR:
                    maxR = number
            
            if color == "green":
                if number > maxG:
                    maxG = number
            
            if color == "blue":
                if number > maxB:
                    maxB = number

            if number > int(limitCubes[color]):
                gameIsPossible = False
                
        
        if gameIsPossible:
            sum += int(id)
        minPowSum += (maxR * maxG * maxB)

    ans = [sum, minPowSum]
        
    return ans

if __name__ == "__main__":
    res = dayTwo("input.txt")
    print(f'Part One - Number of possible games by id sum: {res[0]}')
    print(f'Part Two - Min Sum Power Cubes: {res[1]}')