note
	description: "Summary description for {MISIL}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	MISIL

create
	make

feature --Initialization

	make
			-- Initialization for `Current'.
		do
			memoria := "";
		end

feature {NONE}-- Access
	memoria : STRING;

feature	-- Access
	Activar is
			--
			do
				calcular_falla();
			end

feature -- access
	Fijar_Blanco (objetivo: STRING) is
			--
			do
				calcular_falla();
				memoria := objetivo;
			end

feature -- Access
	Despegar is
			--
			do
				calcular_falla();
			end


feature {NONE}
	calcular_falla is
			--
			local
				r : RANDOM
				t : DATE_TIME
				do
					create r.make;
					create t.make_now;
					r.set_seed (t.seconds);
					if ((r.next_random (t.seconds) \\ 2) = 0 ) then
						r := void;
						r.set_seed (1);
					end
				end

end
