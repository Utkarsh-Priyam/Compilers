.text 0x00400000
.globl main
main:
# Get n1
la $a0, n1
li $v0, 4
syscall
li $v0, 5
syscall
move $s0, $v0

# Get n2
la $a0, n2
li $v0, 4
syscall
li $v0, 5
syscall
move $s4, $v0

la $a0, ($s0)
jal split3
la $s1, ($a1)
la $s2, ($a2)
la $s3, ($a3)

la $a0, ($s4)
jal split3
la $s5, ($a1)
la $s6, ($a2)
la $s7, ($a3)

jal performMult

la $a0, ans1
li $v0, 4
syscall
la $a0, ($s0)
li $v0, 1
syscall
la $a0, ans2
li $v0, 4
syscall
la $a0, ($s4)
li $v0, 1
syscall
la $a0, ans3
li $v0, 4
syscall
jal printResult
li $v0, 4
la $a0, ans4
syscall

j terminate

printResult: # takes the number in t1..5 and prints it with proper formatting
subu $sp $sp 4
sw $ra ($sp)

print1:
beq $t1, 0, print2
la $a0, ($t1)
jal printNormal
j p2p

print2:
beq $t2, 0, print3
la $a0, ($t2)
jal printNormal
j p3p

print3:
beq $t3, 0, print4
la $a0, ($t3)
jal printNormal
j p4p

print4:
beq $t4, 0, print5
la $a0, ($t4)
jal printNormal
j p5p

print5:
la $a0, ($t5)
jal printNormal
j endNormalPrinting

p2p:
la $t0, ($t2)
jal printPadded
p3p:
la $t0, ($t3)
jal printPadded
p4p:
la $t0, ($t4)
jal printPadded
p5p:
la $t0, ($t5)
jal printPadded

endNormalPrinting:

lw $ra ($sp)
addu $sp $sp 4
jr $ra

printNormal: # print normally (from a0)
subu $sp $sp 4
sw $ra ($sp)

li $v0, 1
syscall

lw $ra ($sp)
addu $sp $sp 4
jr $ra

performMult: # takes s1..3 and s5..7, multiplies them, and stores the result into t1..5
subu $sp $sp 4
sw $ra ($sp)

la $t1, ($zero)
la $t2, ($zero)
la $t3, ($zero)
la $t4, ($zero)
la $t5, ($zero)

sum6:
mul $a0, $s1, $s5
jal split3
addu $t1, $t1, $3

sum7:
mul $a0, $s1, $s6
jal split3
addu $t1, $t1, $a2
addu $t2, $t2, $a3

mul $a0, $s2, $s5
jal split3
addu $t1, $t1, $a2
addu $t2, $t2, $a3

sum8:
mul $a0, $s1, $s7
jal split3
addu $t2, $t2, $a2
addu $t3, $t3, $a3

mul $a0, $s2, $6
jal split3
addu $t2, $t2, $a2
addu $t3, $t3, $a3

mul $a0, $s3, $s5
jal split3
addu $t2, $t2, $a2
addu $t3, $t3, $a3

sum9:
mul $a0, $s2, $s7
jal split3
addu $t3, $t3, $a2
addu $t4, $t4, $a3

mul $a0, $s3, $s6
jal split3
addu $t3, $t3, $a2
addu $t4, $t4, $a3

sum10:
mul $a0, $s3, $s7
jal split3
addu $t4, $t4, $a2
addu $t5, $t5, $a3

jal doAllCarrying

lw $ra ($sp)
addu $sp $sp 4
jr $ra

doAllCarrying: # Performs all carrying necessary for t1..5
subu $sp $sp 4
sw $ra ($sp)

la $a1, ($t5)
la $a0, ($t4)
jal additionCarrying
la $t5, ($a1)
la $t4, ($a0)

la $a1, ($t4)
la $a0, ($t3)
jal additionCarrying
la $t4, ($a1)
la $t3, ($a0)

la $a1, ($t3)
la $a0, ($t2)
jal additionCarrying
la $t3, ($a1)
la $t2, ($a0)

la $a1, ($t2)
la $a0, ($t1)
jal additionCarrying
la $t2, ($a1)
la $t1, ($a0)

lw $ra ($sp)
addu $sp $sp 4
jr $ra

additionCarrying: # removes 1e4 from a1 as much as possible, adding the removed parts to a0
subu $sp $sp 4
sw $ra ($sp)

startLoopCarrying:
blt $a1, 10000, endLoopCarrying
subu $a1, $a1, 10000
addu $a0, $a0, 1
j startLoopCarrying
	
endLoopCarrying:

lw $ra ($sp)
addu $sp $sp 4
jr $ra

split3: # takes num in a0, splits into 3 parts all < 10000, so a0 = a1 * 10^8 + a2 * 10^4 + a3
subu $sp $sp 4
sw $ra ($sp)

li $t7, 10000
la $t6, ($a0)
div $t6, $t7
mfhi $a3
mflo $t6
div $t6, $t7
mfhi $a2
mflo $a1

lw $ra ($sp)
addu $sp $sp 4
jr $ra

printPadded: # print t0 fully padded
subu $sp $sp 4
sw $ra ($sp)

la $a0, ($zero)
li $v0, 1

bge $t0, 1000, p0
bge $t0, 100, p1
bge $t0, 10, p2

p3:
syscall
p2:
syscall
p1:
syscall
p0:
la $a0, ($t0)
syscall

lw $ra ($sp)
addu $sp $sp 4
jr $ra


terminate:
li $v0, 10
syscall



.data
nl:
.asciiz "\n"
n1:
.asciiz "Please input the first number to multiply.\n"
n2:
.asciiz "Please input the second number to multiply.\n"
ans1:
.asciiz "The product of "
ans2:
.asciiz " and "
ans3:
.asciiz " is "
ans4:
.asciiz ".\n"
