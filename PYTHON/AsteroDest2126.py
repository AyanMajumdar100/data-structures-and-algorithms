'''
Problem Statement:
You are given an integer mass, which represents the original mass of a planet. 
You are further given an integer array asteroids, where asteroids[i] is the mass of the ith asteroid.
You can arrange for the planet to collide with the asteroids in any arbitrary order. 
If the mass of the planet is greater than or equal to the mass of the asteroid, the asteroid is 
destroyed and the planet gains the mass of the asteroid. Otherwise, the planet is destroyed.
Return true if all asteroids can be destroyed. Otherwise, return false.
'''

'''
Approach: Greedy with Sorting
To maximize the chances of destroying all asteroids, the planet should always collide 
with the smallest available asteroids first. This allows the planet to build up its mass 
before facing larger asteroids. 
1. Sort the asteroids array in ascending order.
2. Iterate through the sorted asteroids. If the current mass is smaller than the asteroid's mass, return false.
3. Otherwise, add the asteroid's mass to the current mass and continue.
4. If the loop completes successfully, return true.
(Note: Python handles arbitrarily large integers automatically, so overflow is not an issue).
'''
class AsteroDest2126:
    def asteroidsDestroyed(self, mass: int, asteroids: list[int]) -> bool:
        # Sort the asteroids to face the smallest ones first (Greedy approach)
        asteroids.sort()
        
        current_mass = mass
        
        # Iterate through each asteroid
        for asteroid in asteroids:
            # If the planet's mass is less than the asteroid, it gets destroyed
            if current_mass < asteroid:
                return False
            # Otherwise, destroy the asteroid and absorb its mass
            current_mass += asteroid
            
        # All asteroids were successfully destroyed
        return True

if __name__ == '__main__':
    try:
        # Get the initial mass of the planet
        mass = int(input("Enter the initial mass of the planet: "))
        
        # Get the masses of the asteroids
        user_input = input("Enter the asteroid masses separated by space: ").strip()
        
        if not user_input:
            print("Result: True")
        else:
            asteroids = list(map(int, user_input.split()))
            
            # Execute the algorithm and print the result
            solver = AsteroDest2126()
            result = solver.asteroidsDestroyed(mass, asteroids)
            
            print(f"All asteroids destroyed? {result}")
            
    except ValueError:
        print("Invalid input. Please enter valid integers.")