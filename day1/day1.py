numbers = {
        "zero" : "0",
        "one" : "1",
        "two" : "2",
        "three" : "3",
        "four" : "4",
        "five" : "5",
        "six" : "6",
        "seven" : "7",
        "eight" : "8",
        "nine" : "9"
    }

digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9"}

def partTwo(input):
    sum = 0
    for line in open(input, "r"):

        #Save all digits found in numbersfound with (digit, indexDigitWasFoundAt)
        numbersfound = list()
        for i, c in enumerate(line):
            if c in digits:
                numbersfound.append((c, i))

        #Save all written numbers found in numbersfound with (numbers[key], indexNumberWasFoundAt)
        for key in numbers:
            for i in range(len(line)):
                if line.startswith(key, i):
                    numbersfound.append((numbers[key], i))

        #Bubble sort sorting numbersFound tuples on indexNumberWasFoundAt
        for x in range(1, (len(numbersfound))):
            for y in range(0, len(numbersfound) - 1):
                if int(numbersfound[y][1]) > int(numbersfound[y + 1][1]):
                    temp = numbersfound[y]
                    numbersfound[y] = numbersfound[y + 1]
                    numbersfound[y + 1] = temp

        res = ""
        res += numbersfound[0][0]
        res += numbersfound[len(numbersfound) - 1][0]
        sum += int(res)
        
    return sum

def partOne(input):
    sum = 0
    for line in open(input, "r"):

        #Save all digits found in numbersfound with digit
        numbersfound = list()
        for c in line:
            if c in digits:
                numbersfound.append(c)

        res = ""
        res += numbersfound[0]
        res += numbersfound[len(numbersfound) - 1]
        sum += int(res)
        
    return sum

if __name__ == "__main__":
    print(f'Part One: {partOne("input.txt")}')
    print(f'Part Two: {partTwo("input.txt")}')
 