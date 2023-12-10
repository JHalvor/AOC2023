engineSchematic = list()

symbols = ["*", "#", "$", "+", "/", "%", "@", "=", "&", "-"]
digits = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0']

def isEnginePart(number, cords):
    y, x = cords

    #Checking for symbols behind the number
    if (y - 1) > -1 and (x - 1) > -1:
        if engineSchematic[y - 1][x - 1] in symbols:
            return True
    
    if (x - 1) > -1:
        if engineSchematic[y][x - 1] in symbols:
            return True
        
    if (y + 1) < len(engineSchematic) and (x - 1) > -1:
        if engineSchematic[y + 1][x - 1] in symbols:
                return True

    #Checking for symbols above and below number
    for i in range(0, len(number)):
        if (y - 1) > -1 and (x + i) > -1 and (x + i) < len(engineSchematic[y]):
            if engineSchematic[y - 1][x + i] in symbols:
                return True
            
        if (y + 1) < len(engineSchematic) and (x + i) > -1 and (x + i) < len(engineSchematic[y]):
            if engineSchematic[y + 1][x + i] in symbols:
                return True

    #Checking for symbols after the number
    if (y - 1) > -1 and (x + len(number)) < len(engineSchematic[y]):
        if engineSchematic[y - 1][x + len(number)] in symbols:
            return True
    
    if (x + len(number)) < len(engineSchematic[y]):
        if engineSchematic[y][x + len(number)] in symbols:
            return True
        
    if (y + 1) < len(engineSchematic) and (x + len(number)) < len(engineSchematic[y]):
        if engineSchematic[y + 1][x + len(number)] in symbols:
                return True

    return False

def isGear(y, x):
    adjecentNumbers = set()

    #Checking for symbols behind the * symbol
    if (y - 1) > -1 and (x - 1) > -1:
        if engineSchematic[y - 1][x - 1] in digits:
            adjecentNumbers.add(findNumber((y - 1), (x - 1)))
    
    if (x - 1) > -1:
        if engineSchematic[y][x - 1] in digits:
            adjecentNumbers.add(findNumber(y, (x - 1)))
        
    if (y + 1) < len(engineSchematic) and (x - 1) > -1:
        if engineSchematic[y + 1][x - 1] in digits:
            adjecentNumbers.add(findNumber((y + 1), (x - 1)))
        
    #Checking for symbols above and below the * symbol
    if (y - 1) > -1 and x > -1 and x < len(engineSchematic[y]):
        if engineSchematic[y - 1][x] in digits:
            adjecentNumbers.add(findNumber((y - 1), x))
        
    if (y + 1) < len(engineSchematic) and x > -1 and x < len(engineSchematic[y]):
        if engineSchematic[y + 1][x] in digits:
            adjecentNumbers.add(findNumber((y + 1), x))

    #Checking for symbols after * symbol
    if (y - 1) > -1 and (x + 1) < len(engineSchematic[y]):
        if engineSchematic[y - 1][x + 1] in digits:
            adjecentNumbers.add(findNumber((y - 1), (x + 1)))
    
    if (x + 1) < len(engineSchematic[y]):
        if engineSchematic[y][x + 1] in digits:
            adjecentNumbers.add(findNumber(y, (x + 1)))
        
    if (y + 1) < len(engineSchematic) and (x + 1) < len(engineSchematic[y]):
        if engineSchematic[y + 1][x + 1] in digits:
            adjecentNumbers.add(findNumber((y + 1), (x + 1)))

    result = [False]
    if len(adjecentNumbers) == 2:
        result[0] = True
        result.append(list(adjecentNumbers)[0])
        result.append(list(adjecentNumbers)[1])
        return result
    return result
    
    
def findNumber(y, x):
    number = engineSchematic[y][x]

    xFTracer = x
    while (xFTracer + 1) < len(engineSchematic[y]):
        if engineSchematic[y][xFTracer + 1] in digits:
            number += engineSchematic[y][xFTracer + 1]
            xFTracer += 1
        else:
            break

    xBTracer = x
    while (xBTracer - 1) > -1:
        if engineSchematic[y][xBTracer - 1] in digits:
            number = engineSchematic[y][xBTracer - 1] + number
            xBTracer -= 1
        else:
            break

    return number

def enginePartSum_PartOne(input):
    sumOfEnginePartNum = 0
    sumOfGearRatio = 0

    for line in open(input, "r"):
        matrix_line = [char for char in line if char != '\n']
        engineSchematic.append(matrix_line)

    number = ""
    for y in range(0, len(engineSchematic)):
        for x in range(0, len(engineSchematic[y])):

            if engineSchematic[y][x] in digits:
                number += engineSchematic[y][x]
                if (x + 1) >= len(engineSchematic[y]):
                    if number != "":
                        if isEnginePart(number, (y, x - (len(number) - 1))):
                            sumOfEnginePartNum += int(number)
                        number = ""

            else:
                if number != "":
                    if isEnginePart(number, (y, x - len(number))):
                        sumOfEnginePartNum += int(number)
                    number = ""

            if engineSchematic[y][x] == '*':
                gear = isGear(y, x)
                if gear[0]:
                    num1 = int(gear[1])
                    num2 = int(gear[2])
                    sumOfGearRatio += (num1 * num2)

    return [sumOfEnginePartNum, sumOfGearRatio]

if __name__ == "__main__":
    res = enginePartSum_PartOne("input.txt")
    print(f'Part One - Sum of Engine Part Numbers: {res[0]}')
    print(f'Part Two - Sum of Gear Ratios: {res[1]}')